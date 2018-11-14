package com.solitary.ksapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.solitary.ksapp.R;
import com.solitary.ksapp.model.Position;

import java.util.ArrayList;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_INTENT_KEY;
import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_ITEM_INDEX;

public class PositionDetailPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_detail_pager);
        ArrayList<Position> positionArrayList = getIntent().getParcelableArrayListExtra(POSITION_LIST_INTENT_KEY);
        int index = getIntent().getIntExtra(POSITION_LIST_ITEM_INDEX, 0);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),positionArrayList);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index, true);


    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Position> positionArrayList;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Position> positionArrayList) {
            super(fragmentManager);
            this.positionArrayList = positionArrayList;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return positionArrayList.size();
        }

        // Returns the fragment to display for that page
        @Override
        public PositionDetailFragment getItem(int position) {

         return PositionDetailFragment.newInstance(positionArrayList.get(position));

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return  positionArrayList.get(position).getTitle();
        }

    }
}

