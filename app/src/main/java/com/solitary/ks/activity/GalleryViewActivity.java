package com.solitary.ks.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.solitary.ks.model.Position;

import com.solitary.ks.R;
import com.solitary.ks.databinding.GalleryViewActivityBinding;


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