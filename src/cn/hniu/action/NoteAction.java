package cn.hniu.action;

import cn.hniu.entity.Note;
import cn.hniu.service.NoteService;

import java.util.ArrayList;

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
}
