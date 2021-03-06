package com.solitary.ksapp.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.solitary.ksapp.db.KSDatabaseHelper;
import com.solitary.ksapp.R;
import com.solitary.ksapp.adapter.PositionListAdapter;
import com.solitary.ksapp.component.ItemOffsetDecoration;
import com.solitary.ksapp.databinding.KsListScreenBinding;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.PositionDataBaseHelper;
import com.solitary.ksapp.listener.PositionClickListener;
import com.solitary.ksapp.model.Position;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_INTENT_KEY;
import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_ITEM_INDEX;


public class PositionListActivity extends AppCompatActivity implements PositionClickListener<Position>,View.OnClickListener {


    protected KsListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    protected PositionListAdapter positionListAdapter;
    protected DataBaseHelper dataBaseHelper;
    private WeakReference<ScaleAnimation> scaleAnimation;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        binding =  DataBindingUtil.setContentView(this, R.layout.ks_list_screen);
        setSupportActionBar(Objects.requireNonNull(binding).toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        WeakReference<Context> contextWeakReference = new WeakReference<>(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(contextWeakReference.get());
        init();


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
        readDataFromDB();
        setScaleAnimation();

    }



    protected void readDataFromDB()
    {

            try {
                PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(KSDatabaseHelper.getInstance(this));
                positionListAdapter.setPositions(positionDataBaseHelper.getAllPositions());
                positionListAdapter.setOnCheckedChangeListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void setScaleAnimation()
    {
        scaleAnimation = new WeakReference<>(new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f));
       // scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.get().setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.get().setInterpolator(bounceInterpolator);
    }


    @Override
    public void onItemClick(Position position, ImageView imageView) {

        Intent intent = new Intent(this,PositionDetailPagerActivity.class  );
        intent.putExtra(EXTRA_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));
        intent.putExtra(POSITION_LIST_ITEM_INDEX, position.getIndex());
        intent.putParcelableArrayListExtra(POSITION_LIST_INTENT_KEY, positionListAdapter.getAllPositions());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView));

        if(position != null) {
            startActivity(intent, options.toBundle());
        }
        else
        {
            Log.v("OnClick", "Position "+position);
            sendClickEvent(position);
        }
    }



    private void sendClickEvent(Position position)
    {
        Bundle bundle = new Bundle();

        bundle.putParcelable(FirebaseAnalytics.Param.ITEM_NAME, position);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "position_item_click");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void onClick(View view) {
        if(scaleAnimation.get() != null)
        {
            view.startAnimation(scaleAnimation.get());
        }

        PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(KSDatabaseHelper.getInstance(this));
        Position position = (Position)view.getTag();

        positionDataBaseHelper.setFavourite(position);
    }

    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }



}
