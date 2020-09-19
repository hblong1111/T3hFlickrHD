package com.longhb.flickrhd.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.longhb.flickrhd.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(Category category);

    @Query("delete from category where id=:id")
    void deleteCategory(int id);

    @Query("delete from category ")
    void deleteAllCategory();

    @Query("select * from category")
    LiveData<List<Category>> getAllCategory();

    @Query("select * from category where is_system=:b")
    MutableLiveData<List<Category>> getAllCategoryAdd(boolean b);

    @Query("update category set title=:title where id=:id")
    void  updateCategory(int id, String title);
}
