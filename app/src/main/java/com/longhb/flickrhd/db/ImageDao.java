package com.longhb.flickrhd.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.longhb.flickrhd.model.Image;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert
    int insertImage(Image image);

    @Query("delete from favourite where id=:id")
    void deleteImage(int id);

    @Query("delete from favourite")
    void deleteAllImage();

    @Query("Select * from favourite")
    LiveData<List<Image>> getAllImage();
}
