package com.longhb.flickrhd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageAdapter;
import com.longhb.flickrhd.databinding.ActivityImagesFavouriteBinding;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.Const;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.ImageAdapterEvent;
import com.longhb.flickrhd.viewmodel.ImagesFavouriteViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImagesFavouriteActivity extends AppCompatActivity implements ImageAdapterEvent {
    private ActivityImagesFavouriteBinding binding;

    private ImagesFavouriteViewModel viewModel;
    private List<Image> images;
    private ImageAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImagesFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();

        createData(savedInstanceState);

        settingRecyclerView();

        changeData(savedInstanceState);

        setupSwipRecycler();
    }

    private void setupSwipRecycler() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Image image = images.get(viewHolder.getAdapterPosition());
                viewModel.deleteImageFavourite(image.getId());
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    private void changeData(Bundle savedInstanceState) {
        viewModel.getListFavourite().observe(this, images1 -> {
            images.clear();
            images.addAll(images1);
            adapter.notifyDataSetChanged();
        });
    }

    private void settingRecyclerView() {
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);

        addLoadMore();
    }

    private void addLoadMore() {
        binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int p, int totalItemsCount, RecyclerView view) {
            }
        });
    }

    private void createData(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(), this, "")).get(ImagesFavouriteViewModel.class);
        images = new ArrayList<>();
        if (savedInstanceState != null) {
            images.addAll((Collection<? extends Image>) savedInstanceState.getSerializable("listImage"));
        }
        adapter = new ImageAdapter(this, images, this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initView() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            startActivity(new Intent(ImagesFavouriteActivity.this, HomeActivity.class));
            finish();
        } else {
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }

    @Override
    public void onItemImageClick(int position) {
        Intent intent = new Intent(ImagesFavouriteActivity.this, ImageDetailActivity.class);
        intent.putExtra(Const.KEY_INTENT_LIST_IMAGE, (Serializable) images);
        intent.putExtra(Const.KEY_INTENT_LIST_IMAGE_POS, position);
        startActivity(intent);
        overridePendingTransition(R.anim.in_right, R.anim.out_left);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listImage", (Serializable) images);
    }
}