package com.longhb.flickrhd;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void animationToolBar(View view, boolean isShow) {
        Animation animation;
        if (isShow) {
            animation = AnimationUtils.loadAnimation(this, R.anim.in_tool_bar);
        } else {
            animation = AnimationUtils.loadAnimation(this, R.anim.out_tool_bar);
        }
        animation.setInterpolator(new LinearInterpolator());

        view.startAnimation(animation);
    }
}
