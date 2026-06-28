package cn.hniu.utils;

import cn.hniu.entity.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    static Properties prop = null;
    static File save = null;
    static int id = 0;

    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("src/notebook.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String path = prop.getProperty("save");
        save = new File(path);
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
        File[] f = save.listFiles();
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
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(save, id + ".note")));
        oos.writeObject(note);
        oos.close();
        id++;
        prop.setProperty("id", String.valueOf(id));
        prop.store(new FileOutputStream("src/notebook.properties"), "");
    }

    public static void writeFile(Note note, int id) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(save, id + ".note")));
        oos.writeObject(note);
        oos.close();
    }

    public static int readZip(File file){
        int err = 0;
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry ze ;
            while ((ze = zis.getNextEntry()) != null) {
                String name = ze.getName();
                if (!name.endsWith(".note")) {
                    err++;
                    zis.closeEntry();
                    System.err.println("文件格式错误");
                    continue;
                }
                ObjectInputStream ois = new ObjectInputStream(zis);
                Note note = (Note) ois.readObject();
                FileUtils.writeFile(note);
                zis.closeEntry();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return err;
    }


    public static void writeZip(File file){
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file+"\\"+"save.zip"));
            for (File f : save.listFiles()) {
                zos.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fis = new FileInputStream(f);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = fis.read(bytes)) != -1) {
                    zos.write(bytes, 0, len);
                }
                fis.close();
                zos.closeEntry();
            }
            zos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
