package com.solitary.ks.component;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    //private int mItemOffset;
    private int left;
    private int top;
    private int right;
    private final int bottom;

    private ItemOffsetDecoration(int itemOffset) {
        bottom = itemOffset;
    }
    public ItemOffsetDecoration(int left,int top,int right,int bottom) {
        this.left = left;
        this.right =  right;
        this.top = top;
        this.bottom = bottom;
    }


    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(left, top, right, bottom);
    }
}