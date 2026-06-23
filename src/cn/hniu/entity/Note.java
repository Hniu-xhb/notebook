package cn.hniu.entity;

import java.io.Serializable;

public class Note implements Serializable {
    private Integer id=-1;
    private String title;
    private String content;

    public Note() {
    }

    public Note(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
