package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageAdapter;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.ImageAdapterEvent;
import com.longhb.flickrhd.util.OnSwipeTouchListener;
import com.longhb.flickrhd.viewmodel.ListImageActivityViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ListImageActivity extends AppCompatActivity implements ImageAdapterEvent {
    private RecyclerView recyclerView2;

    private List<Image> images;
    private ImageAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ListImageActivityViewModel viewModel;
    private MutableLiveData<List<Image>> mListImageNetwork;

    private int perPage = 6;
    private int page = 1;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);

        initView();

        createData();

        settingRecyclerView();

        changeData();
    }

    private void changeData() {
        mListImageNetwork.observe(this, images -> {
            ListImageActivity.this.images.addAll(images);
            adapter.notifyDataSetChanged();
        });
    }

    private void settingRecyclerView() {
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(staggeredGridLayoutManager);

        addLoadMore();
    }

    private void addLoadMore() {
        recyclerView2.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                viewModel.getAllImageNetwork(perPage, page, text);
            }
        });
    }

    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication())).get(ListImageActivityViewModel.class);
        mListImageNetwork = viewModel.getmListImageNetwork();
        text = getIntent().getStringExtra("text");
        viewModel.getAllImageNetwork(perPage, page, text);
        images = new ArrayList<>();
        adapter = new ImageAdapter(this, images, this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initView() {
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);


    }

    @Override
    public void onItemImageClick(int position) {

    }
}