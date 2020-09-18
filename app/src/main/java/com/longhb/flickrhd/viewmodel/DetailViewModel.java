package com.longhb.flickrhd.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.Const;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<List<Image>> mListImage = new MutableLiveData<>();
    private MutableLiveData<Integer> curPage = new MutableLiveData<>();

    public DetailViewModel(Application application, Activity activity) {
        curPage.postValue(activity.getIntent().getIntExtra(Const.KEY_INTENT_LIST_IMAGE_POS, -1));
        mListImage.postValue((List<Image>) activity.getIntent().getSerializableExtra(Const.KEY_INTENT_LIST_IMAGE));

    }

    public MutableLiveData<List<Image>> getmListImage(Activity activity) {
        return mListImage;
    }


    public void setmListImage(MutableLiveData<List<Image>> mListImage) {
        this.mListImage = mListImage;
    }

    public MutableLiveData<Integer> getCurPage(Activity activity) {

        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage.postValue(curPage);
    }
}
