package com.mobikan.ks.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.ads.InterstitialAd;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.mobikan.ks.R;
import com.mobikan.ks.utils.Utils;
import com.mobikan.ks.fragment.SpotListFragment;
import com.mobikan.ks.model.Spot;
import com.mobikan.ks.model.SpotList;

public class SpotActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;
    private InterstitialAd interstitialAd;
    private SpotList spotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        setTitle("");
        mViewPager = findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        /* Interstitial Ads */
        interstitialAd = new InterstitialAd(this, getString(R.string.facebook_fullscreen_id));
        init();
        FrameLayout layout = findViewById(R.id.header_logo);
        layout.setVisibility(View.GONE);

        Log.v("spotList", "spotList "+spotList.getSpots());
        Log.v("spotList", "spotList Filter "+ getFilterList(spotList.getSpots(), "F"));

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

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
                switch (position % 2) {
                    case 0:
                        return "Her";
                    case 1:
                        return "His";

                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                getResources().getDrawable(R.drawable.aromatherapy_massage));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                getResources().getDrawable(R.drawable.deep_tissue_massage));
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/test/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }



    private void init() {
        String response = Utils.readFromAssets("spot.json", getApplicationContext());
        try {
            JSONObject jsonObject = new JSONObject(response);

            spotList = new Gson().fromJson(jsonObject.toString(), SpotList.class);




        } catch (JSONException e) {
            e.printStackTrace();
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
