package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageAdapter;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.ItemImageClick;
import com.longhb.flickrhd.viewmodel.ImageViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ItemImageClick {

    private RecyclerView recyclerView;


    private ImageAdapter imageAdapter;
    private List<Image> imageList;
    private ImageViewModel viewModel;
    private MutableLiveData<List<Image>> data;

    private int per_page=6;
    private int page_=1;
    private  String text="Việt Nam";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        viewModel =new ImageViewModel(getApplication());

        data = viewModel.getListImageNetwork();
        data.observe(this, images -> {
            imageList.addAll(images);
            imageAdapter.notifyDataSetChanged();
        });

        viewModel.getAllImageNetwork(per_page,page_,text);
    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //Khởi tạo dữ liệu
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageList, this);


        //Config adapter
        recyclerView.setAdapter(imageAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                Log.e("OKK",page+"");
                viewModel.getAllImageNetwork(per_page,page,text);
            }
        });
    }

    @Override
    public void onItemImageClick(int position) {
//        viewModel.insertImage(imageList.get(position));
    }
}