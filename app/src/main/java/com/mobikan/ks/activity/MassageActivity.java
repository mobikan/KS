package com.mobikan.ks.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.List;

import com.mobikan.ks.R;
import com.mobikan.ks.utils.Utils;
import com.mobikan.ks.db.DataBaseHelper;
import com.mobikan.ks.db.MassageDataBaseHelper;
import com.mobikan.ks.fragment.MassageFragment;
import com.mobikan.ks.model.Massage;

import static com.mobikan.ks.db.DataBaseHelper.DB_NAME_MASSAGE;


public class MassageActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;
    private int count = 0;
    private List<Massage> massageArrayList;
    private Massage massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        count = readDataFromDb();
        massage =  massageArrayList.get(0);
        setTitle("");
        mViewPager = findViewById(R.id.materialViewPager);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        FrameLayout layout =  findViewById(R.id.header_logo);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(massage.getVideoLink() != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(massage.getVideoLink())));
                }
            }
        });

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

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
                return massageArrayList != null ?massageArrayList.get(position).getTitle() :"";

            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {

                massage = massageArrayList.get(page);
                if(URLUtil.isNetworkUrl(massage.getImageId())) {
                    return HeaderDesign.fromColorResAndUrl(R.color.green, massage.getImageId());
            }
            else
            {
                int imageId = getResources().getIdentifier(Utils.getImageName(massage.getImageId()), "drawable", getPackageName());
                if(imageId == 0)
                {
                    imageId =  R.drawable.thai_massage;
                }
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.green,
                        getResources().getDrawable(imageId));
            }



            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

//        final View logo = findViewById(R.id.logo_white);
//        if (logo != null) {
//            logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mViewPager.notifyHeaderChanged();
//                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
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

}
