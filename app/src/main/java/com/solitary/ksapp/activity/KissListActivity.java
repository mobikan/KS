package com.solitary.ksapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.solitary.ksapp.R;
import com.solitary.ksapp.adapter.KissListAdapter;
import com.solitary.ksapp.component.ItemOffsetDecoration;
import com.solitary.ksapp.databinding.KissListScreenBinding;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.KissDataBaseHelper;
import com.solitary.ksapp.listener.PositionClickListener;
import com.solitary.ksapp.model.Kiss;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class KissListActivity extends AppCompatActivity  implements PositionClickListener<Kiss> {


    private KissListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    private KissListAdapter positionListAdapter;
    private DataBaseHelper dataBaseHelper;
    private KissDataBaseHelper kissDataBaseHelper;
    private  FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.kiss_list_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        init();
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    private void init()
    {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
       // binding.recyclerView.setVisibility(View.INVISIBLE);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Kisses");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int margin =  getResources().getDimensionPixelOffset(R.dimen.recycler_view_margin);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(margin,margin,margin,margin);
        binding.recyclerView.addItemDecoration(itemOffsetDecoration);
        ArrayList<Kiss> kissArrayList = new ArrayList<>();


        positionListAdapter = new KissListAdapter(kissArrayList);
        positionListAdapter.setOnClickListener(this);
        binding.recyclerView.setAdapter(positionListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        readDataFromDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

         if(id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void readDataFromDB()
    {
        try {

            kissDataBaseHelper = new KissDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_KISS));
            List<Kiss> kissArrayList =  kissDataBaseHelper.getAllKisses();
            positionListAdapter.setPositions(kissArrayList);



//            kissDataBaseHelper.deleteAll();
//            for (Kiss kiss : yourList.getKisses())
//            {
//                kiss.setId("KS_"+count);
//                kiss.setImageId(Utils.getImageName(kiss.getTitle()));
//                kissDataBaseHelper.insert(kiss);
//                count++;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(kissDataBaseHelper != null)
        {
            kissDataBaseHelper.close();

        }
    }

    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(Kiss position, ImageView imageView) {
        Intent intent = new Intent(this,KissDetailActivity.class  );
        intent.putExtra(EXTRA_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));
        intent.putExtra("kiss_data", position);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView));

        if(position != null) {
            startActivity(intent, options.toBundle());
        }
        else
        {
            sendClickEvent(position);
        }
    }


    private void sendClickEvent(Kiss position)
    {
        Bundle bundle = new Bundle();

        bundle.putParcelable(FirebaseAnalytics.Param.ITEM_NAME, position);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "kiss_item_click");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
