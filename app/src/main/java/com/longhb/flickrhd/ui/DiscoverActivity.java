package com.longhb.flickrhd.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.ImageAdapter;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.util.Const;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.ImageAdapterEvent;
import com.longhb.flickrhd.viewmodel.ListImageActivityViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements ImageAdapterEvent, View.OnClickListener {
    private RecyclerView recyclerView2;
    private TextView tvTitle;
    private ImageButton btnSearch;
    private EditText edtSearch;
    private ImageButton btnClearText;
    private Group group;


    private List<Image> images;
    private ImageAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ListImageActivityViewModel viewModel;
    private MutableLiveData<List<Image>> mListImageNetwork;
    private MutableLiveData<Integer> mPage;
    private int perPage = 6;
    private int page;
    private String text = "Hanoi";
    private boolean isShowSearch = false;

    private boolean isLodeMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        initView();

        createData(savedInstanceState);

        settingRecyclerView();

        changeData(savedInstanceState);
    }

    private void changeData(Bundle savedInstanceState) {
        mListImageNetwork.observe(this, images -> {
            if ((savedInstanceState == null && this.images.size() == 0) || isLodeMore) {
                DiscoverActivity.this.images.addAll(images);
                adapter.notifyDataSetChanged();

                Log.e("longhbs", DiscoverActivity.this.images.size() + "");
            }
        });

        mPage.observe(this, integer -> page = integer);
    }

    private void settingRecyclerView() {
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(staggeredGridLayoutManager);

        addLoadMore();
    }

    private void addLoadMore() {
        recyclerView2.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int p, int totalItemsCount, RecyclerView view) {
                isLodeMore = true;
                page++;
                viewModel.setmPage(page);
                viewModel.getAllImageNetwork(perPage, page, text);
                Log.e("longhbs", "loadmore" + page + "|");
            }
        });
    }

    private void createData(Bundle savedInstanceState) {
        images = new ArrayList<>();
        if (savedInstanceState != null) {
            images.addAll((Collection<? extends Image>) savedInstanceState.getSerializable("listImage"));
        }
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(), this, text)).get(ListImageActivityViewModel.class);
        mListImageNetwork = viewModel.getmListImageNetwork();
        mPage = viewModel.getmPage();
        adapter = new ImageAdapter(this, images, this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initView() {
        recyclerView2 = findViewById(R.id.recyclerView2);
        tvTitle = findViewById(R.id.textView6);
        btnSearch = findViewById(R.id.btn_search);
        edtSearch = findViewById(R.id.edt_search);
        btnClearText = findViewById(R.id.btn_clear_text);
        group = findViewById(R.id.group);

        btnClearText.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
        finish();
    }

    @Override
    public void onItemImageClick(int position) {
        Intent intent = new Intent(DiscoverActivity.this, ImageDetailActivity.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                if (isShowSearch) {
                    tvTitle.setVisibility(View.VISIBLE);
                    group.setVisibility(View.GONE);
                    String txtInput=edtSearch.getText().toString();
                    if (txtInput!=null&&!txtInput.trim().equals("")&&!txtInput.equals(text)){
                        text = txtInput;
                        page = 1;
                        viewModel.setmPage(page);
                        images.clear();
                        adapter.notifyDataSetChanged();
                        viewModel.getAllImageNetwork(perPage, page, text);
                    }

                    //Ẩn bàn phím
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    tvTitle.setVisibility(View.GONE);
                    group.setVisibility(View.VISIBLE);
                }
                isShowSearch = !isShowSearch;
                break;
            case R.id.btn_clear_text:
                edtSearch.setText("");
                break;
        }
    }
}