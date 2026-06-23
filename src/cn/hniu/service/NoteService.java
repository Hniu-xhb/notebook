package cn.hniu.service;

import cn.hniu.dao.NoteDAO;
import cn.hniu.entity.Note;

import java.io.IOException;
import java.util.ArrayList;

public class NoteService {
    NoteDAO dao = new NoteDAO();

    public boolean addNote(Note note){
        try {
            dao.addNote(note);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public ArrayList<Note> getAllNotes(){
        try {
            return dao.getAllNotes();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public ArrayList<Note> getNotesByTitle(String title){
        try {
            return dao.getNotesByTitle(title);
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
