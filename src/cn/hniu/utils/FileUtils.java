package cn.hniu.utils;

import cn.hniu.entity.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class FileUtils {
    static Properties prop = null;
    static File p = null;
    static int id = 0;

    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("src/notebook.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String path = prop.getProperty("save");
        p = new File(path);
        id = Integer.parseInt(prop.getProperty("id"));
    }

    /**
     * 读取所有笔记文件
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<Note> readFile() throws IOException, ClassNotFoundException {
        File[] f = p.listFiles();
        ArrayList<Note> notes = new ArrayList<>();
        for (File file : f) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Note note = (Note) ois.readObject();
            notes.add(note);
            ois.close();
        }
        notes.sort((o1,o2)->o2.getId()-o1.getId());
        return notes;
    }

    public static void writeFile(Note note) throws IOException {
        note.setId(id);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(p, id + ".note")));
        oos.writeObject(note);
        oos.close();
        id++;
        prop.setProperty("id", String.valueOf(id));
        prop.store(new FileOutputStream("src/notebook.properties"), "");
    }

    public static void writeFile(Note note, int id) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(p, id + ".note")));
        oos.writeObject(note);
        oos.close();
    }

}
