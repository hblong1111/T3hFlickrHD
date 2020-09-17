package com.longhb.flickrhd.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.longhb.flickrhd.db.ImageDao;
import com.longhb.flickrhd.db.ImageDatabase;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.network.ImageModule;
import com.longhb.flickrhd.network.ImageService;
import com.longhb.flickrhd.util.Const;

import java.util.List;

import retrofit2.Call;

public class ImageRepository {
    private final String METHOD = "flickr.favorites.getList";
    private final String METHOD_SEACH = "flickr.photos.search";
    private final String EXTRAS = "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o";//extras
    private final String FORMAT = "json";//extras
    private final String NOJSONCALLBACK = "1";//extras


    private ImageDao imageDao;
    private ImageService imageService;

    public ImageRepository(Application application) {
        this.imageDao = ImageDatabase.getImageDatabase(application).getImageDao();
        this.imageService = ImageModule.getInstance();
    }

    public Call<GetImage> getImagesNetWork(int per_page, int page, String text) {
        return imageService.seachImages(METHOD_SEACH,Const.KEY_TOKEN,EXTRAS,per_page+"",page+"",text,"relevance",FORMAT,NOJSONCALLBACK);
    }

    public void insertImage(Image image) {
         imageDao.insertImage(image);
    }

    public void deleteImage(int id) {
        imageDao.deleteImage(id);
    }

    public void deleteAllImage() {
        imageDao.deleteAllImage();
    }

    public LiveData<List<Image>> getImageFavourite() {
        return imageDao.getAllImage();
    }
}
