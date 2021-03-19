package com.abyte.valet.testan40121.model.artcles;

import com.abyte.valet.testan40121.model.Chapter;
import com.abyte.valet.testan40121.model.Content;

import java.util.List;

public class Article extends Content {
    public Article(Integer img, String author, String info, List<Chapter> chapters) {
        super(img, author, info, chapters);
    }

    public Article(Integer img, String author, String info) {
        super(img, author, info);
    }
}

