package com.longhb.flickrhd.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public MyViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListImageActivityViewModel.class) ){
            return (T) new ListImageActivityViewModel(application);
        }
        return (T) new HomeActivityViewModel(application);
    }
}
