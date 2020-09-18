package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageDetailAdapterViewPager;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.viewmodel.DetailViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;


    private List<Image> images;
    private int pos;
    private ImageDetailAdapterViewPager adapterViewPager;

    private   DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        createData();

        initView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setCurPage(viewPager.getCurrentItem());
    }

    private void settingViewPager(List<Image> images1) {
        images.addAll(images1);
        adapterViewPager = new ImageDetailAdapterViewPager(getSupportFragmentManager(), images);
        viewPager.setAdapter(adapterViewPager);
        viewModel.getCurPage(this).observe(this, integer -> {
            pos = integer;
            viewPager.setCurrentItem(pos);
        });
    }


    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(),this)).get(DetailViewModel.class);
        images = new ArrayList<>();
        viewModel.getmListImage(this).observe(this, images1 -> settingViewPager(images1));
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
        finish();
    }
}