package com.longhb.flickrhd.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityViewModel extends ViewModel {
    private ImageRepository imageRepository;
    private AlertDialog alertDialog;
    private LiveData<List<Category>> mListCategory;


    public HomeActivityViewModel(Application application) {
        this.imageRepository = new ImageRepository(application);
        this.mListCategory = imageRepository.getAllCategory();
    }

    public LiveData<List<Category>> getListCategory() {
        return mListCategory;
    }


    //Category
    public void insertCategory(Category category) {
        imageRepository.insertCategory(category);
    }

    public Call<GetImage> getCategoryNetWork(String s) {
        return imageRepository.getImagesNetWork(1, 1, s);
    }

    public void deleteCategory(int id) {
        imageRepository.deleteCategory(id);
    }

    public void openDialog(Activity activity) {
        BottomSheetDialog dialog = new BottomSheetDialog(activity, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(activity).inflate(R.layout.bottom_sheet, activity.findViewById(R.id.contrain_layout));
        Button btnAddCategory;
        Button btnMyCategory;
        Button btnMyFavourite;
        Button btnCanlce;

        btnAddCategory = view.findViewById(R.id.btn_add_category);
        btnMyCategory = view.findViewById(R.id.btn_my_category);
        btnMyFavourite = view.findViewById(R.id.btn_my_favourite);
        btnCanlce = view.findViewById(R.id.btn_canlce);

        btnCanlce.setOnClickListener(view1 -> dialog.dismiss());
        btnAddCategory.setOnClickListener(view1 -> {
            dialog.dismiss();
            addCategory(activity);
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    private void addCategory(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_category, null, false);
        EditText edtTitle;
        Button btnAdd;
        Button btnCancel;

        edtTitle = view.findViewById(R.id.edt_title);
        btnAdd = view.findViewById(R.id.btn_add);
        btnCancel = view.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());

        btnAdd.setOnClickListener(view1 -> {
            if (!edtTitle.getText().toString().trim().equals("") && edtTitle.getText().toString().trim() != null) {
                getCategoryNetWork(edtTitle.getText().toString()).enqueue(new Callback<GetImage>() {
                    @Override
                    public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                        if (response.body().getPhotos().getPhoto().size()!=0){ 
                            insertCategory(response.body().getPhotos().getPhoto().get(0).getCategory(false, edtTitle.getText().toString(), edtTitle.getText().toString()));

                        }else {
                            Toast.makeText(context, "Chủ đề của bạn chưa được phát triển!", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetImage> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(context, "Bạn chưa nhập Chủ đề!", Toast.LENGTH_SHORT).show();
            }

        });
        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        alertDialog = builder.show();
    }

}
