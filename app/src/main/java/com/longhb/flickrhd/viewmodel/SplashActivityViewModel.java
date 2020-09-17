package com.longhb.flickrhd.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.List;

import retrofit2.Call;

public class SplashActivityViewModel extends ViewModel {
    private ImageRepository repository;

    private LiveData<List<Category>> mListCategory;

    public SplashActivityViewModel(Application application) {
        this.repository = new ImageRepository(application);
        mListCategory=repository.getAllCategory();
    }

    public LiveData<List<Category>> getListCategory() {
        return mListCategory;
    }

    public void insertCategory(Category category){
        repository.insertCategory(category);
    }

    public Call<GetImage> getCategoryNetWork(String s){
        return repository.getImagesNetWork(1,1,s);
    }
}
