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
    private MutableLiveData<List<Image>> mListImageNetwork = new MutableLiveData<>();
    private LiveData<List<Image>> mLitsImageFavourite;
    private LiveData<List<Category>> mListCategory;

    public HomeActivityViewModel(Application application) {
        this.imageRepository = new ImageRepository(application);
        this.mLitsImageFavourite = imageRepository.getImageFavourite();
        this.mListCategory = imageRepository.getAllCategory();
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

    public void getAllImageNetwork(int per_page,int page,String text) {
        imageRepository.getImagesNetWork(per_page, page,text).enqueue(new Callback<GetImage>() {
            @Override
            public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                List<GetImage.Photos.Photo> photos = response.body().getPhotos().getPhoto();
                List<Image> images = new ArrayList<>();
                for (GetImage.Photos.Photo photo :
                        photos) {
                    images.add(photo.getImage());
                }
                if (photos.size()==0){
                }
                mListImageNetwork.postValue(images);

            }

            @Override
            public void onFailure(Call<GetImage> call, Throwable t) {

            }

        });
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
