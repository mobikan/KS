package com.solitary.ks.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ks.R;
import com.solitary.ks.adapter.CustomPagerAdapter;
import com.solitary.ks.component.CalendarHelper;
import com.solitary.ks.component.CheckView;
import com.solitary.ks.databinding.ActivityDetailBinding;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.KSDatabaseHelper;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.fragment.RatingDialogFragment;
import com.solitary.ks.model.Like;
import com.solitary.ks.model.Position;
import com.solitary.ks.utils.Utils;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static com.solitary.ks.utils.Constants.RatingDialog.BUNDLE_POSITION_ID;
import static com.solitary.ks.utils.Constants.RatingDialog.BUNDLE_POSITION_IMAGE_ID;
import static com.solitary.ks.utils.Constants.RatingDialog.BUNDLE_POSITION_TITLE;
import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_RATING_GIVEN_KEY;

public class PositionDetailActivity extends AppCompatActivity implements View.OnClickListener,ValueEventListener{

    private int imageId;
    private Position position;
    private ActivityDetailBinding binding;

    private InterstitialAd mInterstitialAd;

    private WeakReference<Context> contextWeakReference;
    private WeakReference<TextView> likeCountRef;
    private PositionDataBaseHelper positionDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail );
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        contextWeakReference = new WeakReference<>(getApplicationContext());

        position = getIntent().getParcelableExtra("position_data");
        /* Interstitial Ads */

        TextView likeCount = findViewById(R.id.likeCount);
        likeCountRef = new WeakReference<>(likeCount);
        toolbar.setTitle(position.getTitle());
        setTitle(position.getTitle());
       // initAds();
        try {
            init();
        }
        catch (OutOfMemoryError e)
        {
           // Crashlytics.logException(e);
        }
        findViewById(R.id.radialMenu).setOnClickListener(this);

    }


    private void init()
    {
        binding.contentDetail.benefitsTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_benefits, 0, 0, 0);
        binding.contentDetail.detailTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tips_his, 0, 0, 0);

        ImageView imageView = findViewById(R.id.imageView);
        TextView benefits = findViewById(R.id.benefits);
        TextView details = findViewById(R.id.details);


        benefits.setText(position.getBenefits());
        details.setText(position.getDescription());


        binding.title.setOnClickListener(this);
        binding.toolbarLayout.setOnClickListener(this);
        binding.imageView.setOnClickListener(this);

        imageId = getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", getPackageName());

        //imageView.setImageResource(imageId);

        imageView.setImageBitmap(
                Utils.decodeSampledBitmapFromResource(getResources(), imageId, 300, 300));

        setTabLayout();

        setTriedPosition();

        setBookmark();

        setLike();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(contextWeakReference.get());
        positionDataBaseHelper =  new PositionDataBaseHelper(KSDatabaseHelper.getInstance(this)); //new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));

    }

    private void setTabLayout()
    {
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager =  findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(position));
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_tips_his);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_tips_her);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_try_this);
    }

    private void setTriedPosition()
    {
        final CheckView check = findViewById(R.id.tried);
        check.setOnClickListener(this);
        if(position.isTried())
        {
            check.check();
        }
        else
        {
            check.uncheck();
        }
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked())
                {
                    check.uncheck();
                    position.setTried(false);
                    if(positionDataBaseHelper != null) {
                        positionDataBaseHelper.setTried(position);
                    }

                }
                else
                {
                    check.check();
                    position.setTried(true);
                    if(positionDataBaseHelper != null) {
                        positionDataBaseHelper.setTried(position);
                    }
                    //StartAppAd.showAd(PositionDetailActivity.this);

                }
            }
        });
    }


    private void showAppRatingDialog()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
        final boolean isRatingAvail = pref.getBoolean(PREF_RATING_GIVEN_KEY, false);
        if(!isRatingAvail)
        {
            Utils.showAppRatingDialog(getFragmentManager());
        }
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

    private void setBookmark()
    {
        SparkButton favouriteButton = findViewById(R.id.button_favorite);

        favouriteButton.setChecked(position.isFavourite());
        favouriteButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState) {
                    Toast.makeText(getApplicationContext(), "Position bookmarked ", Toast.LENGTH_SHORT).show();
                    //showAds();
                    showAppRatingDialog();
                }
                position.setFavourite(buttonState);
                if(positionDataBaseHelper != null) {
                    positionDataBaseHelper.setFavourite(position);
                }


            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }
    private void setLike()
    {

        final ToggleButton toggleButton = findViewById(R.id.likeToggleButton);
        toggleButton.setBackgroundResource(R.drawable.like_selector);
        toggleButton.setChecked(position.isLiked());

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position.isLiked())
                {
                    position.setLiked(false);
                    toggleButton.setChecked(false);
                    FireBaseQueries.getInstance().resetLike(FireBaseQueries.LIKE_POSITION, position.getId());
                    if(positionDataBaseHelper != null) {
                        positionDataBaseHelper.setLiked(position);
                    }
                    return;
                }
                else
                {
                    position.setLiked(true);
                    toggleButton.setChecked(true);
                    FireBaseQueries.getInstance().updateLike(FireBaseQueries.LIKE_POSITION, position.getId());
                    if(positionDataBaseHelper != null) {
                        positionDataBaseHelper.setLiked(position);
                    }
                    //StartAppAd.showAd(PositionDetailActivity.this);

                }

            }
        });


        FireBaseQueries.getInstance().readLikeById(FireBaseQueries.LIKE_POSITION, position.getId(), this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()) {
            Like like = dataSnapshot.getValue(Like.class);
            if(like != null)
            {

                likeCountRef.get().setText(String.valueOf(like.getNo_of_like()));
            }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {

            startActivity(Utils.openShareImageActivity(contextWeakReference.get(), position.getTitle(), position.getDescription(), imageId));

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
            case R.id.addToReminder:
                try {
                    startActivity(CalendarHelper.onAddEventClicked(position));
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(this, "This feature is not Supported in your Phone.", Toast.LENGTH_SHORT).show();
                }
                break;



            case R.id.rating:
                if(position.getUserRating()== 0) {
                    showRatingDialog();
                }
                else
                {
                    Toast.makeText(this,"Rating already given ." ,Toast.LENGTH_SHORT ).show();
                }
                break;
        }

        //Intent intent = new Intent(this,GalleryViewActivity.class);
       // intent.putExtra("position_data", position);
    }

    private void showRatingDialog()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        RatingDialogFragment dialogFragment = new RatingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POSITION_IMAGE_ID, imageId);
        bundle.putString(BUNDLE_POSITION_ID, position.getId());
        bundle.putString(BUNDLE_POSITION_TITLE, position.getTitle());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }




    @Override
    protected void onDestroy() {

        super.onDestroy();
        if(positionDataBaseHelper != null) {
            positionDataBaseHelper.closeDb();
            positionDataBaseHelper =  null;
        }
    }


}
