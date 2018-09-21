package com.solitary.ks.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.solitary.ks.R;
import com.solitary.ks.fragment.SpotListFragment;
import com.solitary.ks.model.Spot;
import com.solitary.ks.model.SpotList;
import com.solitary.ks.utils.Utils;
import com.startapp.android.publish.adsCommon.StartAppAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpotActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{


    private int[] imageId = {R.drawable.erogenous_spot_female,R.drawable.erogenous_spot_male};
    private SpotList spotList;
    private ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        setTitle("");
        ViewPager mViewPager = findViewById(R.id.materialViewPager);
        mViewPager.addOnPageChangeListener(this);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle("Erogenous Spots");
        }
        headerImageView = findViewById(R.id.htab_header);
        /* Interstitial Ads */

        init();
       // FrameLayout layout = findViewById(R.id.header_logo);
        //layout.setVisibility(View.GONE);
        TabLayout tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(mViewPager);
       // Log.v("spotList", "spotList "+spotList.getSpots());
       // Log.v("spotList", "spotList Filter "+ getFilterList(spotList.getSpots(), "F"));

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
                switch (position % 2) {
                    case 0:
                        return "Her";
                    case 1:
                        return "His";

                }
                return "";
            }
        });


    }


    private void init() {
        String response = Utils.readFromAssets("spot.json", getApplicationContext());
        try {
            JSONObject jsonObject = new JSONObject(response);

            spotList = new Gson().fromJson(jsonObject.toString(), SpotList.class);




        } catch (JSONException e) {
            e.printStackTrace();
        }

       // initAds();
       // showAds();
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
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        headerImageView.setImageResource(imageId[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
