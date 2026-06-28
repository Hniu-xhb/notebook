package cn.hniu.action;

import cn.hniu.entity.Note;
import cn.hniu.service.NoteService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoteAction {
    NoteService service = new NoteService();

    public boolean addNote(Note note){
        return service.addNote(note);
    }

    public ArrayList<Note> getAllNotes(){
        return service.getAllNotes();
    }

    public ArrayList<Note> getNotesByTitle(String title){
        return service.getNotesByTitle(title);
    }

    public int input(File get) throws IOException, ExecutionException, InterruptedException {
        return service.input(get);
    }

    public void output(File get) throws IOException {
        service.output(get);
    }
}
