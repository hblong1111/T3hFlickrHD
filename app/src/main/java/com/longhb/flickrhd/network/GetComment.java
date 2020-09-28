package com.longhb.flickrhd.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetComment {
    @SerializedName("comments")
    @Expose
    public Comments comments;


    public class Comments {

        @SerializedName("photo_id")
        @Expose
        public String photoId;
        @SerializedName("comment")
        @Expose
        public List<Comment_> comment = null;
        public class Comment_ {

            @SerializedName("id")
            @Expose
            public String id;
            @SerializedName("author")
            @Expose
            public String author;
            @SerializedName("author_is_deleted")
            @Expose
            public Integer authorIsDeleted;
            @SerializedName("authorname")
            @Expose
            public String authorname;
            @SerializedName("iconserver")
            @Expose
            public String iconserver;
            @SerializedName("iconfarm")
            @Expose
            public Integer iconfarm;
            @SerializedName("datecreate")
            @Expose
            public String datecreate;
            @SerializedName("permalink")
            @Expose
            public String permalink;
            @SerializedName("path_alias")
            @Expose
            public String pathAlias;
            @SerializedName("realname")
            @Expose
            public String realname;
            @SerializedName("_content")
            @Expose
            public String content;

        }

    }

}
