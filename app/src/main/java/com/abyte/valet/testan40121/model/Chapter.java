package com.abyte.valet.testan40121.model;

import com.abyte.valet.testan40121.R;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String table;
    private Integer img;
    private String info;

    public Chapter(String table, Integer img, String info) {
        this.table = table;
        this.img = img;
        this.info = info;
    }

    public Chapter(String table, String info){
        this.table = table;
        this.info = info;
        this.img = R.drawable.ic1;
    }

    public String getTable() {
        return table;
    }

    public Integer getImg() {
        return img;
    }

    public String getInfo() {
        return info;
    }
}
