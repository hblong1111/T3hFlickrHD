package com.longhb.flickrhd.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class Image {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int height,with;
    private String url,views,url_o;

    public Image(int height, int with, String url, String views, String url_o) {
        this.height = height;
        this.with = with;
        this.url = url;
        this.views = views;
        this.url_o = url_o;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWith() {
        return with;
    }

    public void setWith(int with) {
        this.with = with;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUrl_o() {
        return url_o;
    }

    public void setUrl_o(String url_o) {
        this.url_o = url_o;
    }
}
