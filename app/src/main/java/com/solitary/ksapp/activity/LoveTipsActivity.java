package com.solitary.ksapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ksapp.R;
import com.solitary.ksapp.adapter.TipsListAdapter;
import com.solitary.ksapp.component.ItemOffsetDecoration;
import com.solitary.ksapp.databinding.LoveTipsActivityBinding;
import com.solitary.ksapp.firebase.FireBaseQueries;
import com.solitary.ksapp.listener.PositionClickListener;
import com.solitary.ksapp.model.Tips;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.ArrayList;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ksapp.utils.Constants.WebView.INTENT_URL;
import static com.solitary.ksapp.utils.Constants.WebView.TIPS_COUNT;


public class LoveTipsActivity extends AppCompatActivity  implements PositionClickListener<Tips> {


    private LoveTipsActivityBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    private ArrayList<Tips> tipsArrayList;
    private  TipsListAdapter positionListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.love_tips_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        tipsArrayList = new ArrayList<>();

        init();


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
                        if(tips.getUrl()!= null && tips.getUrl().length()>0){
                            tipsArrayList.add(tips);
                        }
                    }
                    positionListAdapter = new TipsListAdapter(tipsArrayList);
                    binding.recyclerView.setAdapter(positionListAdapter);
                    positionListAdapter.setOnClickListener(LoveTipsActivity.this);
                }

                setTipsListCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTipsListCount()
    {

        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor =  pref.edit();
        editor.putInt(TIPS_COUNT, tipsArrayList.size());
        editor.apply();
    }
    private void init()
    {
        //DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
       // binding.recyclerView.setVisibility(View.INVISIBLE);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Love Tips");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int margin =  getResources().getDimensionPixelOffset(R.dimen.recycler_view_margin);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(margin,margin,margin,margin);
        binding.recyclerView.addItemDecoration(itemOffsetDecoration);



        if(tipsArrayList != null && tipsArrayList.size()>0) {
            positionListAdapter = new TipsListAdapter(tipsArrayList);
            binding.recyclerView.setAdapter(positionListAdapter);
            setTipsListCount();
        }
        else
        {
            readAllTips();
        }



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







    @Override
    public void onItemClick(Tips position, ImageView imageView) {


        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra(INTENT_URL, position.getUrl());
        startActivity(intent);
        StartAppAd.showAd(this);
    }
}
