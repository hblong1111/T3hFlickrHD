package com.longhb.flickrhd.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Activity mActivity;

    public MyViewModelFactory(Application application, Activity mActivity) {
        this.application = application;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListImageActivityViewModel.class) ){
            return (T) new ListImageActivityViewModel(application);
        }else
        if (modelClass.isAssignableFrom(DetailViewModel.class) ){
            return (T) new DetailViewModel(application,mActivity);
        }
        return (T) new HomeActivityViewModel(application);
    }
}
