package com.solitary.ksapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.solitary.ksapp.R;
import com.solitary.ksapp.model.Position;
import com.solitary.ksapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_INTENT_KEY;
import static com.solitary.ksapp.utils.Constants.PositionDetail.POSITION_LIST_ITEM_INDEX;

public class PositionDetailPagerActivity extends AppCompatActivity {


    private Position position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_detail_pager);
        final ArrayList<Position> positionArrayList = getIntent().getParcelableArrayListExtra(POSITION_LIST_INTENT_KEY);
        int index = getIntent().getIntExtra(POSITION_LIST_ITEM_INDEX, 0);
        position = positionArrayList.get(index);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),positionArrayList);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index, true);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbarID));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(position.getTitle());

       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int index) {
               position =  positionArrayList.get(index);
               setTitle(position.getTitle());
           }

           @Override
           public void onPageScrollStateChanged(int index) {

           }
       });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

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
        public CharSequence getPageTitle(int index) {
            return  position.getTitle();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {

            int imageId = getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", getPackageName());
            startActivity(Utils.openShareImageActivity(getApplicationContext(), position.getTitle(), position.getDescription(), imageId));

        }


        return super.onOptionsItemSelected(item);
    }

}

