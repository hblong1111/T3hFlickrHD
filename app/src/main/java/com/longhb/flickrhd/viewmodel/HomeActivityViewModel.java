package com.longhb.flickrhd.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityViewModel extends ViewModel {
    private ImageRepository imageRepository;
    private LiveData<List<Category>> mListCategory;

    public HomeActivityViewModel(Application application) {
        this.imageRepository = new ImageRepository(application);
        this.mListCategory = imageRepository.getAllCategory();
    }

    public LiveData<List<Category>> getListCategory() {
        return mListCategory;
    }


    //Category
    public void insertCategory(Category category){
        imageRepository.insertCategory(category);
    }

    public Call<GetImage> getCategoryNetWork(String s){
        return imageRepository.getImagesNetWork(1,1,s);
    }

    public void deleteCategory(int id){
        imageRepository.deleteCategory(id);
    }
}
