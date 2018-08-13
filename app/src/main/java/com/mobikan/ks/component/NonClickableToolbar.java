package com.mobikan.ks.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonClickableToolbar extends Toolbar {

    public NonClickableToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
        public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
