package com.solitary.ks.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ks.R;

import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.KissDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Like;
import com.solitary.ks.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_RATING_GIVEN_KEY;

public class KissDetailActivity extends AppCompatActivity implements View.OnClickListener,ValueEventListener{

    private int imageId = 0;
    private Kiss kiss;
    private InterstitialAd mInterstitialAd;
    private KissDataBaseHelper kissDataBaseHelper;
    private TextView likeCount;

    private WeakReference<Context> activityWeakReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kiss_detail_activity );
        kiss = getIntent().getParcelableExtra("kiss_data");

        activityWeakReference = new WeakReference<>(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(kiss.getTitle());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //supportPostponeEnterTransition();
        ImageView imageView = findViewById(R.id.kissImage);
        TextView benefits = findViewById(R.id.kissDetails);

        imageId = getResources().getIdentifier(kiss.getImageId(), "drawable", getPackageName());
        imageView.setImageResource(imageId);
        benefits.setText(kiss.getDetail());

        //initAds();
        setLike();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        kissDataBaseHelper = new KissDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_KISS));
        Log.v("Like", "like "+kissDataBaseHelper.getLike(kiss.getId()));
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
        toggleButton.setChecked(kiss.isLike());

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kiss.isLike())
                {
                    kiss.setLike(false);
                    toggleButton.setChecked(false);
                    FireBaseQueries.getInstance().resetLike(FireBaseQueries.LIKE_KISS, kiss.getId());
                    kissDataBaseHelper.setLike(kiss);
                    return;
                }
                else
                {
                    kiss.setLike(true);
                    toggleButton.setChecked(true);
                    FireBaseQueries.getInstance().updateLike(FireBaseQueries.LIKE_KISS, kiss.getId());
                    kissDataBaseHelper.setLike(kiss);
                    //showAds();
                    showAppRatingDialog();
                }

            }
        });

        likeCount  = findViewById(R.id.likeCount);
        databaseReference = FireBaseQueries.getInstance().readLikeById(FireBaseQueries.LIKE_KISS, kiss.getId(),this);
    }

    private DatabaseReference databaseReference;
    private void showAppRatingDialog()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
        final boolean isRatingAvail = pref.getBoolean(PREF_RATING_GIVEN_KEY, false);
        if(!isRatingAvail)
        {
            Utils.showAppRatingDialog(getFragmentManager());
        }
    }

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

                startActivity(Utils.openShareImageActivity(activityWeakReference.get(), kiss.getTitle(), kiss.getDetail(), imageId));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(kissDataBaseHelper != null)
        {
            kissDataBaseHelper.close();
        }
        if(databaseReference != null)
        {
            databaseReference.removeEventListener(this);
        }
    }
}
