package com.longhb.flickrhd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.ui.ImageDetailActivity;
import com.longhb.flickrhd.util.OnSwipeTouchListener;
import com.longhb.flickrhd.viewmodel.DetailViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;
import com.squareup.picasso.Picasso;

public class ImageDetailFaragment extends Fragment {

    private Image image;

    private PhotoView photoView;

    public ImageDetailFaragment(Image image) {
        this.image = image;
    }

    public ImageDetailFaragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        photoView = view.findViewById(R.id.photoView);

        if (image == null) {
            image = (Image) savedInstanceState.getSerializable("iamgeDetail");
        }
        Glide.with(getContext()).load(image.getUrl_o()).placeholder(R.drawable.place_image_detail).into(photoView);

        photoView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.e("longhbs", i + "|" + i2 + "|" + i3);
            }
        });

        return view;
    }

    private void openDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_detail, getActivity().findViewById(R.id.contrain_layout));

        Button btnAddFavourite;
        Button btnSetWall;
        Button btnDownload;
        Button btnHome;
        Button btnCanlce;

        btnAddFavourite = view.findViewById(R.id.btn_add_favourite);
        btnSetWall = view.findViewById(R.id.btn_set_wall);
        btnDownload = view.findViewById(R.id.btn_download);
        btnHome = view.findViewById(R.id.btn_home);
        btnCanlce = view.findViewById(R.id.btn_canlce);

        btnCanlce.setOnClickListener(view1 -> dialog.cancel());
        dialog.setContentView(view);
        dialog.create();
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("iamgeDetail", image);
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
