package com.solitary.ks.listener;

import android.widget.ImageView;

import com.solitary.ks.model.Position;

public interface PositionClickListener<T> {

    public void onItemClick(T position, ImageView imageView);

}
