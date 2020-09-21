package com.longhb.flickrhd.adpater;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longhb.flickrhd.fragment.ImageDetailFaragment;
import com.longhb.flickrhd.model.Image;
import com.longhb.flickrhd.ui.ImageDetailActivity;

import java.util.List;

public class ImageDetailAdapterViewPager extends FragmentPagerAdapter {
    private List<Image> list;

    public ImageDetailAdapterViewPager(@NonNull FragmentManager fm, List<Image> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new ImageDetailFaragment(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
