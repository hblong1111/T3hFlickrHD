package com.longhb.flickrhd.util;

public interface CategoryAdapterEvent {
    void onClickItem(int pos);
    void onClickItemChoose(int pos);

    void itemLongClick(int position);
}
