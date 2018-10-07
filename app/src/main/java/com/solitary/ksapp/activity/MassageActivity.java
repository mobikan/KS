package com.solitary.ksapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.solitary.ksapp.R;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.MassageDataBaseHelper;
import com.solitary.ksapp.fragment.MassageFragment;
import com.solitary.ksapp.model.Massage;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.List;

import static com.solitary.ksapp.db.DataBaseHelper.DB_NAME_MASSAGE;


public class MassageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private int count = 0;
    private List<Massage> massageArrayList;
    private ImageView headerImageView;
    private Massage massage;
    private FloatingActionButton floatingActionButton;

    private int[] imageId = {R.drawable.aromatherapy_massage,R.drawable.swedish_massage,R.drawable.deep_tissue_massage,R.drawable.trigger_point_massage,R.drawable.sports_massage
    ,R.drawable.aromatherapy_massage,R.drawable.hot_stone_massage,R.drawable.foot_refleology,R.drawable.shiatsu_massage,R.drawable.thai_massage,
    R.drawable.four_hand_massage,R.drawable.body_to_body,R.drawable.lingam_massage,R.drawable.yoni_massage,R.drawable.tantric_massage,R.drawable.sopy_massage };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage_list_screen);
        count = readDataFromDb();
        if(massageArrayList != null && massageArrayList.size()>0) {
            massage = massageArrayList.get(0);
            init();

        }
        else
        {
            Toast.makeText(this, "Unable to load data,Try after some time !", Toast.LENGTH_SHORT).show();
        }


        final Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle("Sensual Massages");
        }

        headerImageView = findViewById(R.id.htab_header);

        //if(massage.getVideoLink() == null) {
            //floatingActionButton.setVisibility(View.GONE);
        //}
    }


    private void init()
    {
        floatingActionButton = findViewById(R.id.playVideo);
        ViewPager mViewPager = findViewById(R.id.materialViewPager);
        TabLayout tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
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


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(massage.getVideoLink() != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(massage.getVideoLink())));
                }
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
        if(massageArrayList != null && massageArrayList.size()>position) {
            setTitle(massageArrayList.get(position).getTitle());
            massage = massageArrayList.get(position);
            if(massage.getVideoLink() == null)
            {
                floatingActionButton.setVisibility(View.GONE);
            }
            else {
                floatingActionButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
