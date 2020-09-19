package com.longhb.flickrhd.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.ui.ImageDetailActivity;
import com.longhb.flickrhd.viewmodel.DetailViewModel;
import com.squareup.picasso.Picasso;

public class ImageDetailFaragment extends Fragment {
    private static ImageDetailFaragment INSTANCE;
    private Image image;

    private PhotoView photoView;

    public ImageDetailFaragment(Image image) {
        this.image = image;
    }

    private ImageDetailFaragment() {

    }

    public static ImageDetailFaragment getInstance(Image image) {
        if (INSTANCE == null) {
            INSTANCE = new ImageDetailFaragment(image);
        }
        INSTANCE.setImage(image);
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        photoView = view.findViewById(R.id.photoView);
        Log.e("imgaeif","h:" +image.getH_o());
        photoView.setMinimumHeight(image.getH_o());
        photoView.setMaxHeight(image.getH_o());
        Glide.with(getContext()).load(image.getUrl_o()).placeholder(R.drawable.place_image_detail).into(photoView);

        return view;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
