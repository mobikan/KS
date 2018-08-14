package com.mobikan.ks.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mobikan.ks.R;
import com.mobikan.ks.db.DataBaseHelper;
import com.mobikan.ks.firebase.FireBaseQueries;
import com.mobikan.ks.model.Like;

import java.io.IOException;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{

   // private HomePageActivityBinding binding;
   private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  binding = DataBindingUtil.setContentView(this,R.layout.home_page_activity);
         setContentView(R.layout.home_page_activity);
        init();
        setTitle("KaamSutra");
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);

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
        toolbar.setTitle("Home Page");

        RelativeLayout positionId = findViewById(R.id.positionId);
        positionId.setOnClickListener(this);

        RelativeLayout spotId = findViewById(R.id.spotId);
        spotId.setOnClickListener(this);

        RelativeLayout kissesId = findViewById(R.id.kissesId);
        kissesId.setOnClickListener(this);

        RelativeLayout massageId = findViewById(R.id.massageId);
        massageId.setOnClickListener(this);

        RelativeLayout sexTipsId = findViewById(R.id.sexTipsId);
        sexTipsId.setOnClickListener(this);


        RelativeLayout breathId = findViewById(R.id.breathId);
        breathId.setOnClickListener(this);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        try {
            dataBaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

            case R.id.sexTipsId:
                break;
            case R.id.breathId:
                break;



        }
    }
}
