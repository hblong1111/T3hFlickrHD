package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageDetailAdapterViewPager;
import com.longhb.flickrhd.databinding.ActivityImageDetailBinding;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.viewmodel.DetailViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {

    private ActivityImageDetailBinding binding;

    private List<Image> images;
    private int pos = -1;
    private ImageDetailAdapterViewPager adapterViewPager;

    public DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        createData();
    }

    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(), this, "")).get(DetailViewModel.class);
        images = new ArrayList<>();


        viewModel.getmListImage().observe(this, images1 -> settingViewPager(images1));
    }

    private void settingViewPager(List<Image> images1) {
        images.addAll(images1);
        adapterViewPager = new ImageDetailAdapterViewPager(getSupportFragmentManager(), images, viewModel);
        binding.viewPager.setAdapter(adapterViewPager);
        viewModel.getCurPage().observe(this, integer -> {
            if (pos == -1) {
                pos = integer;
                binding.viewPager.setCurrentItem(pos);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
        finish();
    }


    public void onClick(View view) {
      long insert=  viewModel.insertImageFavourite(images.get(binding.viewPager.getCurrentItem()));
      if (insert>=0){
          Toast.makeText(this, "Thêm ảnh vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
      }else {
          Toast.makeText(this, "Gặp lỗi khi thêm ảnh vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
      }
    }
}