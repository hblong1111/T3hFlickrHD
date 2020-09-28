package com.longhb.flickrhd.model;

public class Comment {

    private String id, urlAvatar, content, iconFam, iconSever;

    public Comment(String id, String urlAvatar, String content, String iconFam, String iconSever) {
        this.id = id;
        this.urlAvatar = urlAvatar;
        this.content = content;
        this.iconFam = iconFam;
        this.iconSever = iconSever;
    }

    public String getId() {
        return id;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getContent() {
        return content;
    }

    public String getIconFam() {
        return iconFam;
    }

    public String getIconSever() {
        return iconSever;
    }
}
