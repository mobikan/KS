package com.solitary.ksapp.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.solitary.ksapp.R;
import com.solitary.ksapp.adapter.CustomPagerAdapter;
import com.solitary.ksapp.component.CalendarHelper;
import com.solitary.ksapp.component.CheckView;
import com.solitary.ksapp.databinding.ActivityDetailBinding;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.KSDatabaseHelper;
import com.solitary.ksapp.db.PositionDataBaseHelper;
import com.solitary.ksapp.firebase.FireBaseQueries;
import com.solitary.ksapp.fragment.RatingDialogFragment;
import com.solitary.ksapp.model.Like;
import com.solitary.ksapp.model.Position;
import com.solitary.ksapp.utils.Utils;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_ID;
import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_IMAGE_ID;
import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_TITLE;
import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_RATING_GIVEN_KEY;

public class PositionDetailFragment extends android.support.v4.app.Fragment implements View.OnClickListener,ValueEventListener{

    private int imageId;
    private Position position;
    private ActivityDetailBinding binding;

    private InterstitialAd mInterstitialAd;

    private WeakReference<Context> contextWeakReference;
    private WeakReference<TextView> likeCountRef;
    private PositionDataBaseHelper positionDataBaseHelper;


    public static  PositionDetailFragment newInstance(Position position)
    {
        PositionDetailFragment fragment = new PositionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("position_data",position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =  DataBindingUtil.inflate(inflater,R.layout.activity_detail,container, false);
       // binding = DataBindingUtil.setContentView(this,R.layout.activity_detail );


        contextWeakReference = new WeakReference<>(getActivity().getApplicationContext());

        if(getArguments() != null)
        {
            position = getArguments().getParcelable("position_data");
           // binding.toolbarID.setTitle(position.getTitle());
        }

//        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbarID);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Interstitial Ads */

        TextView likeCount = binding.getRoot().findViewById(R.id.likeCount);
        likeCountRef = new WeakReference<>(likeCount);


        // initAds();
        try {
            init();
        }
        catch (OutOfMemoryError e)
        {
            // Crashlytics.logException(e);
        }
        binding.getRoot().findViewById(R.id.radialMenu).setOnClickListener(this);

        return binding.getRoot();
    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail );
//        setContentView(R.layout.activity_detail);
//        Toolbar toolbar = findViewById(R.id.toolbarID);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//
//        contextWeakReference = new WeakReference<>(getApplicationContext());
//
//        position = getIntent().getParcelableExtra("position_data");
//        /* Interstitial Ads */
//
//        TextView likeCount = findViewById(R.id.likeCount);
//        likeCountRef = new WeakReference<>(likeCount);
//        toolbar.setTitle(position.getTitle());
//        setTitle(position.getTitle());
//       // initAds();
//        try {
//            init();
//        }
//        catch (OutOfMemoryError e)
//        {
//           // Crashlytics.logException(e);
//        }
//        findViewById(R.id.radialMenu).setOnClickListener(this);
//
//    }


    private void init()
    {
        binding.contentDetail.benefitsTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_benefits, 0, 0, 0);
        binding.contentDetail.detailTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tips_his, 0, 0, 0);

        ImageView imageView = binding.getRoot().findViewById(R.id.imageView);
        TextView benefits = binding.getRoot().findViewById(R.id.benefits);
        TextView details = binding.getRoot().findViewById(R.id.details);


        benefits.setText(position.getBenefits());
        details.setText(position.getDescription());



//        binding.toolbarLayout.setOnClickListener(this);
        binding.imageView.setOnClickListener(this);

        imageId = getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", getActivity().getPackageName());

        //imageView.setImageResource(imageId);

        imageView.setImageBitmap(
                Utils.decodeSampledBitmapFromResource(getResources(), imageId, 300, 300));
        imageView.setVisibility(View.INVISIBLE);

        setTabLayout();

        setTriedPosition();

        setBookmark();

        setLike();
    }

    @Override
    public void onStart() {
        super.onStart();
        //DataBaseHelper dataBaseHelper = new DataBaseHelper(contextWeakReference.get());
        positionDataBaseHelper =  new PositionDataBaseHelper(KSDatabaseHelper.getInstance(getContext())); //new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));

    }

    private void setTabLayout()
    {
        TabLayout tabLayout = binding.getRoot().findViewById(R.id.tab_layout);

        ViewPager viewPager =  binding.getRoot().findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(position));
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_tips_his);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_tips_her);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_try_this);
    }



    private void setTriedPosition()
    {
        final CheckView check = binding.getRoot().findViewById(R.id.tried);
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
                    //StartAppAd.showAd(PositionDetailFragment.this);

                }
            }
        });
    }


    private void showAppRatingDialog()
    {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
        final boolean isRatingAvail = pref.getBoolean(PREF_RATING_GIVEN_KEY, false);
        if(!isRatingAvail)
        {
            Utils.showAppRatingDialog(getActivity().getFragmentManager());
        }
    }



    private void setBookmark()
    {
        SparkButton favouriteButton = binding.getRoot().findViewById(R.id.button_favorite);

        favouriteButton.setChecked(position.isFavourite());
        favouriteButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState) {
                    Toast.makeText(getActivity().getApplicationContext(), "Position bookmarked ", Toast.LENGTH_SHORT).show();
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

        final ToggleButton toggleButton = binding.getRoot().findViewById(R.id.likeToggleButton);
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
                    //StartAppAd.showAd(PositionDetailFragment.this);

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
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//
//        inflater.inflate(R.menu.share_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }



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
                    Toast.makeText(getContext(), "This feature is not Supported in your Phone.", Toast.LENGTH_SHORT).show();
                }
                break;



            case R.id.rating:
                if(position.getUserRating()== 0) {
                    showRatingDialog();
                }
                else
                {
                    Toast.makeText(getContext(),"Rating already given ." ,Toast.LENGTH_SHORT ).show();
                }
                break;
        }

        //Intent intent = new Intent(this,GalleryViewActivity.class);
       // intent.putExtra("position_data", position);
    }

    private void showRatingDialog()
    {
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        Fragment prev = getActivity().getFragmentManager().findFragmentByTag("dialog");
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




//    @Override
//    protected void onDestroy() {
//
//        super.onDestroy();
//        if(positionDataBaseHelper != null) {
//            positionDataBaseHelper.closeDb();
//            positionDataBaseHelper =  null;
//        }
//    }


}
