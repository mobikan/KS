package com.solitary.ks.activity;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ks.R;
import com.solitary.ks.databinding.HomePageActivityBinding;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.model.Like;
import com.solitary.ks.model.Position;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{

   // private HomePageActivityBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private HomePageActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding =
                DataBindingUtil.setContentView(this,R.layout.home_page_activity);
         setContentView(R.layout.home_page_activity);
        init();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);


        /* Facebook Ads */
        adView = new AdView(this, getString(R.string.facebook_banner_id), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.v("FB Ads", "Fb Ads Error"+adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.v("FB Ads", "Fb Ads Loaded"+ad.getPlacementId());
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        /* Interstitial Ads */
        interstitialAd = new InterstitialAd(this, getString(R.string.facebook_fullscreen_id));
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.v("FB Ads", "Fb InterstitialAd Error"+adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }

    private void sendClickEvent(String id,String itemName)
    {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image_click");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }



    private void init()
    {
        FireBaseQueries.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_home_page);


        RelativeLayout positionId = findViewById(R.id.positionId);

         positionId.setOnClickListener(this);

        RelativeLayout spotId = findViewById(R.id.spotId);
        spotId.setOnClickListener(this);

        RelativeLayout kissesId = findViewById(R.id.kissesId);
        kissesId.setOnClickListener(this);

        RelativeLayout massageId = findViewById(R.id.massageId);
        massageId.setOnClickListener(this);

        RelativeLayout sexTipsId = findViewById(R.id.favouriteId);
        sexTipsId.setOnClickListener(this);


        RelativeLayout triedId = findViewById(R.id.triedId);
        triedId.setOnClickListener(this);


        upDateRatings();
       // PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
        //positionDataBaseHelper.getAllFavouritePositions();
    }


    private void upDateRatings()
    {
        DataBaseHelper dataBaseHelper =  new DataBaseHelper(getApplicationContext());
        final PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
        FireBaseQueries.getInstance().readAllLikes(FireBaseQueries.LIKE_POSITION, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Like like = postSnapshot.getValue(Like.class);
                        Log.v("LIke ", "Like " + like.getNo_of_like());
                        Position position = new Position();
                        position.setId(like.getId());
                        position.setRating((int) like.getRating());
                        positionDataBaseHelper.setRating(position);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.positionId:
                startActivity(new Intent(this, PositionListActivity.class));
                sendClickEvent("positionId","PositionListActivity");
                break;
            case R.id.spotId:
                startActivity(new Intent(this, SpotActivity.class));
                sendClickEvent("spotId","SpotActivity");
                break;

            case R.id.kissesId:
                startActivity(new Intent(this, KissListActivity.class));
                sendClickEvent("kissesId","KissListActivity");
                break;
            case R.id.massageId:
                startActivity(new Intent(this, MassageActivity.class));
                sendClickEvent("massageId","MassageActivity");
                break;

            case R.id.favouriteId:
                startActivity(new Intent(this, FavouritePositionListActivity.class));
                sendClickEvent("sexTipsId","FavouritePositionListActivity");
                break;
            case R.id.triedId:
                startActivity(new Intent(this, TriedPositionsList.class));
                sendClickEvent("breathId","TriedPositionsList");
                break;



        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (interstitialAd != null) {
            interstitialAd.loadAd();
        }
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
