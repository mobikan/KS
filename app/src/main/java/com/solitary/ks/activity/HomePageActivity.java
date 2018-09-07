package com.solitary.ks.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ks.R;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.firebase.ExitDialogFragment;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.fragment.AppRatingDialogFragment;
import com.solitary.ks.model.Like;
import com.solitary.ks.model.Position;
import com.solitary.ks.model.Tips;
import com.solitary.ks.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

import static com.solitary.ks.utils.Constants.TermsAndCondition.INTENT_TIPS_LIST_KEY;


public class HomePageActivity extends AppCompatActivity implements View.OnClickListener,ValueEventListener{

   // private HomePageActivityBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;

    private PositionDataBaseHelper positionDataBaseHelper;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ArrayList<Tips> tipsArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                DataBindingUtil.setContentView(this,R.layout.home_page_activity);
        setContentView(R.layout.home_page_activity);
       // MobileAds.initialize(this, getString(R.string.admob_app_id));
        init();
        initDrawer();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//        AdView adView = findViewById(R.id.adView);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);



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

        DataBaseHelper dataBaseHelper =  new DataBaseHelper(getApplicationContext());
        positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
        //readAllTips();
    }


    private void initDrawer()
    {
        drawerLayout = findViewById(R.id.activity_main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.share:
                        Utils.shareApp(HomePageActivity.this);
                        break;
                    case R.id.privacy:
                        startActivity(new Intent(HomePageActivity.this,PrivacyPolicyActivity.class));
                        break;
                    case R.id.rating:
                        Utils.openAppOnGooglePlay(HomePageActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;

            }

        });


    }

    private void readAllTips()
    {

        FireBaseQueries.getInstance().readAllTips(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Tips tips = postSnapshot.getValue(Tips.class);
                        tipsArrayList.add(tips);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if(dataSnapshot.exists()) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Like like = postSnapshot.getValue(Like.class);
                Log.v("LIke ", "Like " + Objects.requireNonNull(like).getNo_of_like());
                Position position = new Position();
                position.setId(like.getId());
                position.setRating((int) like.getRating());
                positionDataBaseHelper.setRating(position);
            }
        }

        positionDataBaseHelper.closeDb();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void upDateRatings()
    {

        FireBaseQueries.getInstance().readAllLikes(FireBaseQueries.LIKE_POSITION, this);

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
                //openTips();
                startActivity(new Intent(this, TriedPositionsList.class));
                sendClickEvent("breathId","TriedPositionsList");
                break;



        }
    }

    private void openTips()
    {
        Intent intent = new Intent(this, LoveTipsActivity.class);
        intent.putParcelableArrayListExtra(INTENT_TIPS_LIST_KEY, tipsArrayList);
        startActivity(intent);
        sendClickEvent("tipsId","LoveTipsActivity");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START)) {
            //drawer is open
            try {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }catch (Exception e)
            {
                super.onBackPressed();
            }
        }
        else
        {

            showAppRatingDialog(getFragmentManager());
           // super.onBackPressed();
        }

    }

    public static void showAppRatingDialog(FragmentManager fragmentManager)
    {

        FragmentTransaction ft =  fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("ExitDialogFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ExitDialogFragment dialogFragment = new ExitDialogFragment();

        dialogFragment.show(ft, "ExitDialogFragment");
    }
}
