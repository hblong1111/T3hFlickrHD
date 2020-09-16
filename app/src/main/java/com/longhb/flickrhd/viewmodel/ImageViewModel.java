package com.longhb.flickrhd.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImagesFavourite;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageViewModel extends ViewModel {
    private ImageRepository imageRepository;
    private MutableLiveData<List<Image>> mListImageNetwork = new MutableLiveData<>();
    private LiveData<List<Image>> mLitsImageFavourite;

    public ImageViewModel(Application application) {
        this.imageRepository = new ImageRepository(application);
        this.mLitsImageFavourite = imageRepository.getImageFavourite();
    }

    public MutableLiveData<List<Image>> getListImageNetwork() {
        return mListImageNetwork;
    }

    public LiveData<List<Image>> getLitsImageFavourite() {
        return mLitsImageFavourite;
    }

    public void insertImage(Image image) {
        imageRepository.insertImage(image);
    }

    public void deleteImage(int id) {
        imageRepository.deleteImage(id);
    }

    public void deleteAllImage() {
        imageRepository.deleteAllImage();
    }

    public void getAllImageNetwork() {
        imageRepository.getImagesNetWork(20, 1).enqueue(new Callback<GetImagesFavourite>() {
            @Override
            public void onResponse(Call<GetImagesFavourite> call, Response<GetImagesFavourite> response) {
                List<GetImagesFavourite.Photos.Photo> photos = response.body().getPhotos().getPhoto();
                List<Image> images = new ArrayList<>();
                for (GetImagesFavourite.Photos.Photo photo :
                        photos) {
                    images.add(photo.getImage());
                }
                mListImageNetwork.postValue(images);
            }

            @Override
            public void onFailure(Call<GetImagesFavourite> call, Throwable t) {

            }
        });
    }

}
