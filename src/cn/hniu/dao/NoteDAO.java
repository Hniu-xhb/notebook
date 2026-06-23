package cn.hniu.dao;

import cn.hniu.entity.Note;
import cn.hniu.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;

public class NoteDAO {
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

}
