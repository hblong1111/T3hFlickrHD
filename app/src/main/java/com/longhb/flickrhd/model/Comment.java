package com.longhb.flickrhd.model;


import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Comment {

    private String id, urlAvatar, content, authorName, time;

    public Comment(String id, String urlAvatar, String content, String authorName, String time) {
        this.id = id;
        this.urlAvatar = urlAvatar;
        this.content = content;
        this.authorName = authorName;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getAuthorName() {
        return authorName;
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

    public String convertTime() {
        String result;
        long time = System.currentTimeMillis() - Long.parseLong(getTime()) * 1000;
        int phut = Integer.parseInt(String.format("%d",
                TimeUnit.MILLISECONDS.toMinutes(time)
        ));
        if (phut < 60) {
            result = phut + " phút";
        }else if (phut < 1440) {
            result = phut/60 + " giờ";
        }else if (phut < 43830) {
            result = phut/1440 + " ngày";
        }else if (phut < 525960) {
            result = phut/43830 + " tháng";
        }else {
            result = phut/525960 + " năm";
        }
        return result;
    }
}
