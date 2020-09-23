package com.longhb.flickrhd.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.widget.AutoScrollHelper;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.longhb.flickrhd.BaseActivity;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.adpater.CategoryAdapter;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.util.CategoryAdapterEvent;
import com.longhb.flickrhd.util.Const;
import com.longhb.flickrhd.util.EndlessRecyclerViewScrollListener;
import com.longhb.flickrhd.util.OnSwipeTouchListener;
import com.longhb.flickrhd.viewmodel.HomeActivityViewModel;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements CategoryAdapterEvent {

    private RecyclerView recyclerView;
    private TextView tvBottomSheet;
    private ConstraintLayout ct;
    private ConstraintLayout toolbar;
    private ImageButton btnClose;
    private TextView tvNumberSelect;
    private ImageButton btnDelete;
    private ImageButton btnSelectAll;
    private ImageButton btnUnSelect;


    private HomeActivityViewModel viewModel;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;


    private LinearLayoutManager manager = new LinearLayoutManager(this);
    private int numberChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        createData();

        settingRecyclerView();

        openBottomSheet();

        settingTollbar();

    }

    private void settingTollbar() {
        btnDelete.setOnClickListener(view -> {
            btnDeleteClick();
        });

        btnSelectAll.setOnClickListener(view -> btnSelectAllClick());

        btnUnSelect.setOnClickListener(view -> btnUnSelectClick());

        btnClose.setOnClickListener(view -> btnCloseClick());
    }

    private void btnUnSelectClick() {
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setChoose(false);
            categoryAdapter.notifyItemChanged(i);
        }
        viewModel.removeAllListChoose();
        numberChoose = 0;
        viewModel.setNumberSelect(numberChoose);
    }

    private void btnSelectAllClick() {
        numberChoose = categories.size();
        viewModel.setNumberSelect(numberChoose);
        viewModel.removeAllListChoose();
        for (int i = 0; i < categories.size(); i++) {
            viewModel.getListIdItemChoose().add(categories.get(i).getId());
            categories.get(i).setChoose(true);
            categoryAdapter.notifyItemChanged(i);
        }

    }

    private void btnCloseClick() {

        categoryAdapter.setChoose(false);
        Log.e("longhbs","close");
        toolbar.setVisibility(View.GONE);
        recyclerView.setPadding(0,0,0,0);
        viewModel.getListIdItemChoose().clear();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).isChoose()){
                categories.get(i).setChoose(false);
                categoryAdapter.notifyItemChanged(i);
            }
        }

    }

    private void btnDeleteClick() {
        for (int i = 0; i < viewModel.getListIdItemChoose().size(); i++) {
            viewModel.deleteCategory(viewModel.getListIdItemChoose().get(i));
        }
        numberChoose = 0;
        viewModel.setNumberSelect(numberChoose);
    }

    private void openBottomSheet() {
        tvBottomSheet.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                viewModel.openDialog(HomeActivity.this,DiscoverActivity.class);
            }
        });
    }


    private void settingRecyclerView() {
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(manager);
    }

    private void createData() {
        viewModel = ViewModelProviders.of(this, new MyViewModelFactory(getApplication(), this, "")).get(HomeActivityViewModel.class);
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories, this);

        viewModel.getListCategory().observe(this, categories1 -> {
            if (categories1.size() == 0) {
                for (int i = 0; i < Const.CATEGORYS.length; i++) {
                    int finalI = i;
                    viewModel.getCategoryNetWork(Const.CATEGORYS[i]).enqueue(new Callback<GetImage>() {
                        @Override
                        public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                            viewModel.insertCategory(response.body().getPhotos().getPhoto().get(0).getCategory(true, Const.CATEGORYS_TITLE[finalI], Const.CATEGORYS[finalI]));
                        }

                        @Override
                        public void onFailure(Call<GetImage> call, Throwable t) {
                            Log.e("longhb", "Lỗi thêm category mặc định : " + t.getMessage());
                        }
                    });
                }
            } else {
                categories.clear();
                categories.addAll(categories1);
                categoryAdapter.notifyDataSetChanged();
            }
        });

        //Listener event choose number change
        viewModel.getNumberSelect().observe(this, s -> tvNumberSelect.setText(s));
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        tvBottomSheet = findViewById(R.id.tv_bottom_sheet);
        ct = findViewById(R.id.ct);
        toolbar = findViewById(R.id.toolbar);
        btnClose = findViewById(R.id.btn_close);
        tvNumberSelect = findViewById(R.id.tv_number_select);
        btnDelete = findViewById(R.id.btn_delete);
        btnSelectAll = findViewById(R.id.btn_select_all);
        btnUnSelect = findViewById(R.id.btn_un_select);

        recyclerView.setPadding(0, toolbar.getHeight(), 0, 0);

    }



    //Oclick ItemCategoryAdapter
    @Override
    public void onClickItem(int pos) {
        Intent intent = new Intent(HomeActivity.this, ListImageActivity.class);
        intent.putExtra("text", categories.get(pos).getText());
        intent.putExtra("title", categories.get(pos).getTitle());
        startActivity(intent);
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    public void onClickItemChoose(int pos) {
        if (categories.get(pos).isChoose()) {
            viewModel.setNumberSelect(--numberChoose);
            viewModel.removeItemChoose(categories.get(pos).getId());
        } else {
            viewModel.addItemChoose(categories.get(pos).getId());
            viewModel.setNumberSelect(++numberChoose);

        }
        categories.get(pos).setChoose(!categories.get(pos).isChoose());
        categoryAdapter.notifyItemChanged(pos);
    }

    @Override
    public void itemLongClick(int position) {
        if (categoryAdapter.isChoose() == false) {
            categoryAdapter.setChoose(true);
            numberChoose = 1;
            toolbar.setVisibility(View.VISIBLE);
            recyclerView.setPadding(0, 154, 0, 0);
            viewModel.setNumberSelect(numberChoose);
            categories.get(position).setChoose(true);
            categoryAdapter.notifyItemChanged(position);
            viewModel.addItemChoose(categories.get(position).getId());
            //TODO add event choose item
        }
    }
}