package com.solitary.ks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.solitary.ks.R;

import com.solitary.ks.model.Kiss;

public class KissDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Kiss kiss;
    private InterstitialAd mInterstitialAd;

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

        int imageId = getResources().getIdentifier(kiss.getImageId(), "drawable", getPackageName());
        imageView.setImageResource(imageId);
        benefits.setText(kiss.getDetail());

        initAds();
    }

    private void initAds()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        showAds();
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

        Intent intent = new Intent(this,GalleryViewActivity.class);
        intent.putExtra("position_data", kiss);
    }
}
