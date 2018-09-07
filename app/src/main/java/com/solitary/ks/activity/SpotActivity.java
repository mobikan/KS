package com.solitary.ks.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.solitary.ks.R;
import com.solitary.ks.fragment.SpotListFragment;
import com.solitary.ks.model.Spot;
import com.solitary.ks.model.SpotList;
import com.solitary.ks.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SpotActivity extends AppCompatActivity {


    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SpotList spotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        ViewPager mViewPager = findViewById(R.id.materialViewPager);
        collapsingToolbarLayout = findViewById(R.id.htab_collapse_toolbar);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        setTitle("Erogenous Spot");

        /* Interstitial Ads */

        init();
//        FrameLayout layout = findViewById(R.id.header_logo);
//        layout.setVisibility(View.GONE);


        TabLayout  tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 2) {
                    case 0: {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(SpotListFragment.BUNDLE_KEY_SPOT, getFilterList(spotList.getSpots(), "F"));
                        return SpotListFragment.newInstance(bundle);
                    }
                    case 1: {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(SpotListFragment.BUNDLE_KEY_SPOT, getFilterList(spotList.getSpots(), "M"));
                        return SpotListFragment.newInstance(bundle);
                    }
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(SpotListFragment.BUNDLE_KEY_SPOT, spotList.getSpots());
                        return SpotListFragment.newInstance(bundle);
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Her";
                    case 1:
                        return "His";

                }
                return "";
            }
        });



    }

//    private InterstitialAd mInterstitialAd;
//    private void initAds()
//    {
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }
//
//    private void showAds()
//    {
//        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//    }


    private void init() {
        String response = Utils.readFromAssets("spot.json", getApplicationContext());
        try {
            JSONObject jsonObject = new JSONObject(response);

            spotList = new Gson().fromJson(jsonObject.toString(), SpotList.class);




        } catch (JSONException e) {
            e.printStackTrace();
        }
        initMultiplier();
       // initAds();
       // showAds();
    }

    private void initMultiplier()
    {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aromatherapy_massage);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark);
                   // collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    //collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e("TAG", "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.colorPrimary)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.colorPrimaryDark)
            );
        }
    }

    private ArrayList<Spot> getFilterList(ArrayList<Spot> list, String type) {
        ArrayList<Spot> filterList = new ArrayList<>();
        for (Spot spot : list) {
            if (spot.getType().equalsIgnoreCase(type)) {
                filterList.add(spot);
            }
        }
        return filterList;
    }


}
