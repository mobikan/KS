package com.mobikan.ks.listener;

import android.widget.ImageView;

import com.mobikan.ks.model.Position;

public interface PositionClickListener<T> {

    public void onItemClick(T position, ImageView imageView);

}
