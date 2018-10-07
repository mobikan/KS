package com.solitary.ksapp.listener;

import android.widget.ImageView;

public interface PositionClickListener<T> {

    public void onItemClick(T position, ImageView imageView);

}
