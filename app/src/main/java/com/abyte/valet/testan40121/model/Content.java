package com.abyte.valet.testan40121.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Content implements Serializable {
    private Integer img;
    private String info;
    private String author;

    public List<Chapter> chapters;

    public Content(Integer img, String author, String info, List<Chapter> chapters) {
        this.author = author;
        this.img = img;
        this.info = info;
        this.chapters = chapters;
    }

    public Content(Integer img, String author, String info) {
        this.author = author;
        this.img = img;
        this.info = info;
        this.chapters = new ArrayList<>();
    }

    public String getInfo() {
        return info;
    }

    public Integer getImg() {
        return img;
    }

    public Chapter getChapter(int ind){
        return chapters.get(ind);
    }

    public void addChapter(Chapter chapter){
        chapters.add(chapter);
    }

    public void addChapter(String table, String info){
        chapters.add(new Chapter(table, info));
    }

    public void addChapter(String table, Integer img, String info){
        chapters.add(new Chapter(table, img, info));
    }

    public String getAuthor() {
        return author;
    }


}