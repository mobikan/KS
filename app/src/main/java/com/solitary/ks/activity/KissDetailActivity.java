package com.solitary.ks.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ks.R;

import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.KissDataBaseHelper;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Like;
import com.solitary.ks.utils.Utils;

public class KissDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private int imageId = 0;
    private Kiss kiss;
    private InterstitialAd mInterstitialAd;
    private KissDataBaseHelper kissDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kiss_detail_activity );
        kiss = getIntent().getParcelableExtra("kiss_data");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(kiss.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //supportPostponeEnterTransition();
        ImageView imageView = (ImageView) findViewById(R.id.kissImage);
        TextView benefits = findViewById(R.id.kissDetails);

        imageId = getResources().getIdentifier(kiss.getImageId(), "drawable", getPackageName());
        imageView.setImageResource(imageId);
        benefits.setText(kiss.getDetail());

        initAds();
        setLike();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        kissDataBaseHelper = new KissDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_KISS));

        ImageView shareImage = findViewById(R.id.share);
        shareImage.setOnClickListener(this);


    }

    private void initAds()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    private void showAds()
    {
        try {
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
        catch (Exception e)
        {
            Log.e("TAG", "Error in Ads"+e.getMessage());
        }
    }

    private void setLike()
    {

        final ToggleButton toggleButton = findViewById(R.id.likeToggleButton);
        toggleButton.setBackgroundResource(R.drawable.like_selector);
        toggleButton.setChecked(kiss.isLiked());

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kiss.isLiked())
                {
                    kiss.setLiked(false);
                    toggleButton.setChecked(false);
                    FireBaseQueries.getInstance().resetLike(FireBaseQueries.LIKE_KISS, kiss.getId());
                    kissDataBaseHelper.setLiked(kiss);
                    return;
                }
                else
                {
                    kiss.setLiked(true);
                    toggleButton.setChecked(true);
                    FireBaseQueries.getInstance().updateLike(FireBaseQueries.LIKE_KISS, kiss.getId());
                    kissDataBaseHelper.setLiked(kiss);
                    showAds();
                }

            }
        });

        final TextView likeCount = findViewById(R.id.likeCount);
        FireBaseQueries.getInstance().readLikeById(FireBaseQueries.LIKE_KISS, kiss.getId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Like like = dataSnapshot.getValue(Like.class);
                    if(like != null)
                    {
                        likeCount.setText(String.valueOf(like.getNo_of_like()));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.share:
                Utils.openShareImageActivity(this, kiss.getTitle(), kiss.getDetail(), imageId);
                break;
        }
    }
}
