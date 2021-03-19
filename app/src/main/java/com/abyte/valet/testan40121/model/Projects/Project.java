package com.abyte.valet.testan40121.model.Projects;

import com.abyte.valet.testan40121.model.Chapter;
import com.abyte.valet.testan40121.model.Content;

import java.util.List;

public class Project extends Content {
    public Project(Integer img, String author, String info, List<Chapter> chapters) {
        super(img, author, info, chapters);
    }

    public Project(Integer img, String author, String info) {
        super(img, author, info);
    }
}

