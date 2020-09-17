package com.longhb.flickrhd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.longhb.flickrhd.R;
import com.longhb.flickrhd.model.Category;
import com.longhb.flickrhd.network.GetImage;
import com.longhb.flickrhd.util.Const;
import com.longhb.flickrhd.viewmodel.MyViewModelFactory;
import com.longhb.flickrhd.viewmodel.SplashActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startHomeActivity();
    }

    private void startHomeActivity() {
       new CountDownTimer(2500,100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.in_right, R.anim.out_left);
                finish();
            }
        }.start();
    }
}