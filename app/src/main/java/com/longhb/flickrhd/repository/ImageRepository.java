package com.longhb.flickrhd.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.longhb.flickrhd.db.CategoryDao;
import com.longhb.flickrhd.db.ImageDao;
import com.longhb.flickrhd.db.ImageDatabase;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetComment;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.network.ImageModule;
import com.longhb.flickrhd.network.ImageService;
import com.longhb.flickrhd.util.Const;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

public class ImageRepository {
    private static final String METHOD_GET_COMMENT = "flickr.photos.comments.getList";
    private final String METHOD_SEACH = "flickr.photos.search";
    private final String EXTRAS = "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o";//extras
    private final String FORMAT = "json";//extras
    private final String NOJSONCALLBACK = "1";//extras


    private ImageDao imageDao;
    private CategoryDao categoryDao;
    private ImageService imageService;

    public ImageRepository(Application application) {
        this.imageDao = ImageDatabase.getImageDatabase(application).getImageDao();
        this.categoryDao = ImageDatabase.getImageDatabase(application).getCategoryDao();
        this.imageService = ImageModule.getInstance();
    }


    //Image network
    public Call<GetImage> getImagesNetWork(int per_page, int page, String text) {
        return imageService.seachImages(METHOD_SEACH, Const.KEY_TOKEN, EXTRAS, per_page + "", page + "", text, "relevance", FORMAT, NOJSONCALLBACK);
    }


    //Image local
    public long insertImage(Image image) {
        return imageDao.insertImage(image);
    }

    public void deleteImage(String id) {
        imageDao.deleteImage(id);
    }

    public void deleteAllImage() {
        imageDao.deleteAllImage();
    }

    public LiveData<List<Image>> getImageFavourite() {
        return imageDao.getAllImage();
    }

    //Category
    public void insertCategory(Category category) {
        categoryDao.insertCategory(category);
    }

    public void deleteCategory(int id) {
        categoryDao.deleteCategory(id);
    }

    public void deleteAllCategory() {
        categoryDao.deleteAllCategory();
    }

    public void updateCategory(int id, String title) {
        categoryDao.updateCategory(id, title);
    }

    public LiveData<List<Category>> getAllCategory() {
        return categoryDao.getAllCategory();
    }

    public LiveData<List<Category>> getAllCategoryAdd() {
        return categoryDao.getAllCategoryAdd(false);
    }
    
    //Comment
    
    public Call<GetComment> getCommentForImage(String photoId){
        return imageService.getAllComment(METHOD_GET_COMMENT,Const.KEY_TOKEN,photoId,"json","1");
    }

}
