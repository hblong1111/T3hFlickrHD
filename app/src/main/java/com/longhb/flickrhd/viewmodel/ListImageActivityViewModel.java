package com.longhb.flickrhd.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListImageActivityViewModel extends ViewModel {
    private ImageRepository repository;
    private MutableLiveData<List<Image>> mListImageNetwork=new MutableLiveData<>();

    public ListImageActivityViewModel(Application application) {
        this.repository = new ImageRepository(application);
    }

    public MutableLiveData<List<Image>> getmListImageNetwork() {
        return mListImageNetwork;
    }

    public void getAllImageNetwork(int perPage, int page, String txt){
        repository.getImagesNetWork(perPage,page,txt).enqueue(new Callback<GetImage>() {
            @Override
            public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                List<Image> images=new ArrayList<>();
                for (GetImage.Photos.Photo photo:response.body().getPhotos().getPhoto()
                     ) {
                    images.add(photo.getImage());
                }

                mListImageNetwork.postValue(images);
            }

            @Override
            public void onFailure(Call<GetImage> call, Throwable t) {

            }
        });
    }
}
