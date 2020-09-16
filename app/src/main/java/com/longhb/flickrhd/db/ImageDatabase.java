package com.longhb.flickrhd.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.longhb.flickrhd.model.Image;

@Database(entities = {Image.class}, version = 1, exportSchema = false)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract ImageDao getImageDao();

    public static ImageDatabase getImageDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), ImageDatabase.class, "Image").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}
