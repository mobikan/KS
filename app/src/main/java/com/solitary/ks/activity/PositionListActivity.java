package com.solitary.ks.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.solitary.ks.adapter.PositionListAdapter;
import com.solitary.ks.component.ItemOffsetDecoration;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.listener.PositionClickListener;
import com.solitary.ks.model.Like;
import com.solitary.ks.model.Position;

import java.util.ArrayList;
import com.solitary.ks.R;
import com.solitary.ks.databinding.KsListScreenBinding;


public class PositionListActivity extends AppCompatActivity implements PositionClickListener<Position>,View.OnClickListener {


    protected KsListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    protected PositionListAdapter positionListAdapter;
    protected DataBaseHelper dataBaseHelper;
    private ScaleAnimation scaleAnimation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScaleAnimation();
        binding =  DataBindingUtil.setContentView(this, R.layout.ks_list_screen);
        setSupportActionBar(binding.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        init();
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    protected void setTitle()
    {
        binding.toolbar.setTitle(R.string.title_position_list);
    }

    private void init()
    {
        setTitle();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int margin =  getResources().getDimensionPixelOffset(R.dimen.recycler_view_margin);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(margin,margin,margin,margin);
        binding.recyclerView.addItemDecoration(itemOffsetDecoration);
        ArrayList<Position> positions = new ArrayList<>();

        positionListAdapter = new PositionListAdapter(positions);
        positionListAdapter.setOnClickListener(this);
        binding.recyclerView.setAdapter(positionListAdapter);
       // readDataFromDB();


    }


    @Override
    protected void onStart() {
        super.onStart();
        readDataFromDB();
    }

    protected void readDataFromDB()
    {
        try {
            PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
            positionListAdapter.setPositions(positionDataBaseHelper.getAllPositions());
            positionListAdapter.setOnCheckedChangeListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setScaleAnimation()
    {
        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
    }


    @Override
    public void onItemClick(Position position, ImageView imageView) {
        Intent intent = new Intent(this,PositionDetailActivity.class  );
        intent.putExtra(EXTRA_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));
        intent.putExtra("position_data", position);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView));

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(scaleAnimation);
        PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
        Position position = (Position)view.getTag();

        positionDataBaseHelper.setFavourite(position);
    }
}
