package cn.hniu.service;

import cn.hniu.dao.NoteDAO;
import cn.hniu.entity.Note;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    public int input(File get) throws IOException, ExecutionException, InterruptedException {
        return dao.input(get);
    }

    public void output(File get) throws IOException {
        dao.output(get);
    }
}
