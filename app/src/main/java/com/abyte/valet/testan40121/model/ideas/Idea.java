package com.abyte.valet.testan40121.model.ideas;

import com.abyte.valet.testan40121.model.Chapter;
import com.abyte.valet.testan40121.model.Content;

import java.util.List;

public class Idea extends Content {
    public Idea(Integer img, String author, String info, List<Chapter> chapters) {
        super(img, author, info, chapters);
    }

    public Idea(Integer img, String author, String info) {
        super(img, author, info);
    }
}