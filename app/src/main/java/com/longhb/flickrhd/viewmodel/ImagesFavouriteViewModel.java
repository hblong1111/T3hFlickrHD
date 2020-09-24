package com.longhb.flickrhd.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.List;

public class ImagesFavouriteViewModel extends ViewModel {
    private ImageRepository repository;
    private Activity mActivity;

    private MutableLiveData<List<Image>> listFavourite = new MutableLiveData<>();

    public ImagesFavouriteViewModel(Application application, Activity activity) {
        repository = new ImageRepository(application);
        mActivity=activity;
        getAllImagesFavourite();
    }

    public MutableLiveData<List<Image>> getListFavourite() {
        return listFavourite;
    }

    public void getAllImagesFavourite() {
        repository.getImageFavourite().observe((LifecycleOwner) mActivity, images -> listFavourite.postValue(images));
    }

    public void deleteImageFavourite(String id){
        repository.deleteImage(id);
    }
}
