package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.CategoryAdapter;
import com.longhb.flickrhd.adpater.ImageAdapter;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.util.CategoryAdapterEvent;
import com.longhb.flickrhd.util.Const;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.ItemImageClick;
import com.longhb.flickrhd.util.OnSwipeTouchListener;
import com.longhb.flickrhd.viewmodel.HomeActivityViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements CategoryAdapterEvent {

    private RecyclerView recyclerView;

    private HomeActivityViewModel viewModel;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        createData();

        settingRecyclerView();
    }

    private void settingRecyclerView() {
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication())).get(HomeActivityViewModel.class);
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories, this);

        viewModel.getListCategory().observe(this, categories1 -> {
            if (categories1.size() == 0) {
                for (int i = 0; i < Const.CATEGORYS.length; i++) {
                    int finalI = i;
                    viewModel.getCategoryNetWork(Const.CATEGORYS[i]).enqueue(new Callback<GetImage>() {
                        @Override
                        public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                            viewModel.insertCategory(response.body().getPhotos().getPhoto().get(0).getCategory(true, Const.CATEGORYS_TITLE[finalI]));
                        }

                        @Override
                        public void onFailure(Call<GetImage> call, Throwable t) {
                            Log.e("longhb", "Lỗi thêm category mặc định : "+t.getMessage());
                        }
                    });
                }
            } else {
                categories.clear();
                categories.addAll(categories1);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}