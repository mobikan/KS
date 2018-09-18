package com.solitary.ks.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.solitary.ks.R;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.MassageDataBaseHelper;
import com.solitary.ks.fragment.MassageFragment;
import com.solitary.ks.model.Massage;
import com.solitary.ks.utils.Utils;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.List;
import java.util.Objects;

import static com.solitary.ks.db.DataBaseHelper.DB_NAME_MASSAGE;


public class MassageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private int count = 0;
    private List<Massage> massageArrayList;
    private Massage massage;
    private InterstitialAd mInterstitialAd;
    private ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage_list_screen);
        count = readDataFromDb();
        massage =  massageArrayList.get(0);
        setTitle("");
         ViewPager mViewPager = findViewById(R.id.materialViewPager);
        mViewPager.addOnPageChangeListener(this);
        /* Interstitial Ads */
        //initAds();
        //showAds();
        final Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle("Sensual Massages");
        }

        headerImageView = findViewById(R.id.htab_header);


        TabLayout tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                        return MassageFragment.newInstance(massageArrayList.get(position));

            }

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return massageArrayList != null ? massageArrayList.get(position).getTitle() :"";

            }


        });


    }



    private int readDataFromDb()
    {
      try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
            MassageDataBaseHelper massageDataBaseHelper = new MassageDataBaseHelper(dataBaseHelper.openDatabase(DB_NAME_MASSAGE));
            massageArrayList = massageDataBaseHelper.getAllMassage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    return massageArrayList != null ? massageArrayList.size() : 0;
    }



    private void initAds()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void showAds()
    {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
