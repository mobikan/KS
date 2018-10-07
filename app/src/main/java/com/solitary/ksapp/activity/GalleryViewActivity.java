package com.solitary.ksapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.solitary.ksapp.model.Position;

import com.solitary.ksapp.R;
import com.solitary.ksapp.databinding.GalleryViewActivityBinding;


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