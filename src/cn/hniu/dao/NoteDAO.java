package cn.hniu.dao;

import cn.hniu.entity.Note;
import cn.hniu.utils.FileUtils;
import cn.hniu.utils.Input;
import cn.hniu.utils.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class NoteDAO {
    private static final ExecutorService exe = Executors.newFixedThreadPool(3,r->{
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });


    public boolean addNote(Note note) throws IOException {
        if(note.getId()!=-1){
            FileUtils.writeFile(note,note.getId());
        }else{
            FileUtils.writeFile(note);
        }
        return true;
    }


    public ArrayList<Note> getAllNotes() throws IOException, ClassNotFoundException {
        return FileUtils.readFile();
    }

    public ArrayList<Note> getNotesByTitle(String title) throws IOException, ClassNotFoundException {
        ArrayList<Note> notes = getAllNotes();
        ArrayList<Note> notesByTitle = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTitle().contains(title)) {
                notesByTitle.add(note);
            }
        }
        return notesByTitle;
    }

    public static int input(File get) throws IOException, ExecutionException, InterruptedException {
        return exe.submit(new Input(get)).get();

    }

    public static void output(File get) throws IOException {
        exe.submit(new Output(get));
    }

}
