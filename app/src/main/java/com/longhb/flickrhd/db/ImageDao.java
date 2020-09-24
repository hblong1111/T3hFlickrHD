package com.longhb.flickrhd.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;

import com.longhb.flickrhd.model.Image;

import java.util.List;

@Dao
public interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertImage(Image image);

    @Query("delete from favourite where id=:id")
    void deleteImage(String id);

    @Query("delete from favourite")
    void deleteAllImage();

    @Query("Select * from favourite")
    LiveData<List<Image>> getAllImage();
}
