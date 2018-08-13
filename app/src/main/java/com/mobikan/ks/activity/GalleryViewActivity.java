package com.mobikan.ks.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobikan.ks.model.Position;

import com.mobikan.ks.R;
import com.mobikan.ks.databinding.GalleryViewActivityBinding;


public class GalleryViewActivity extends AppCompatActivity {

    private GalleryViewActivityBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.gallery_view_activity);

        init();
    }

    private void init()
    {
        Position position = getIntent().getParcelableExtra("position_data");
       // binding.imageView.setImageResource(R.drawable.placeholder_icon);


    }

}