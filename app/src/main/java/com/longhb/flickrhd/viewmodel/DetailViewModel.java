package com.longhb.flickrhd.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Comment;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetComment;
import com.longhb.flickrhd.repository.ImageRepository;
import com.longhb.flickrhd.util.Const;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private ImageRepository repository;
    private MutableLiveData<List<Image>> mListImage = new MutableLiveData<>();
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<List<Comment>>();

    public DetailViewModel(Application application, Activity activity) {
        repository = new ImageRepository(application);
        curPage.postValue(activity.getIntent().getIntExtra(Const.KEY_INTENT_LIST_IMAGE_POS, -1));
        mListImage.postValue((List<Image>) activity.getIntent().getSerializableExtra(Const.KEY_INTENT_LIST_IMAGE));
    }

    public MutableLiveData<List<Comment>> getComments() {
        return comments;
    }

    public MutableLiveData<List<Image>> getmListImage() {
        return mListImage;
    }

    public MutableLiveData<Integer> getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage.postValue(curPage);
    }


    public long insertImageFavourite(Image image) {
        return repository.insertImage(image);
    }

    public void getAllCommentNetwork(String idPhoto) {
        repository.getCommentForImage(idPhoto).enqueue(new Callback<GetComment>() {
            @Override
            public void onResponse(Call<GetComment> call, Response<GetComment> response) {
                List<Comment> comments = new ArrayList<>();
                if (response.body().comments.comment.size() != 0) {
                    for (int i = 0; i < response.body().comments.comment.size(); i++) {
                        GetComment.Comments.Comment_ comment_ = response.body().comments.comment.get(i);
                        if (comment_.getComment() != null) {
                            comments.add(comment_.getComment());
                        }
                    }
                }

                DetailViewModel.this.comments.postValue(comments);
            }

            @Override
            public void onFailure(Call<GetComment> call, Throwable t) {

            }
        });
    }
}
