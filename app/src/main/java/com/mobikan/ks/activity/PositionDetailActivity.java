package com.mobikan.ks.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mobikan.ks.R;
import com.mobikan.ks.utils.Utils;
import com.mobikan.ks.adapter.CustomPagerAdapter;
import com.mobikan.ks.component.CalendarHelper;
import com.mobikan.ks.component.CheckView;
import com.mobikan.ks.databinding.ActivityDetailBinding;
import com.mobikan.ks.db.DataBaseHelper;
import com.mobikan.ks.db.PositionDataBaseHelper;
import com.mobikan.ks.firebase.FireBaseQueries;
import com.mobikan.ks.fragment.RatingDialogFragment;
import com.mobikan.ks.model.Like;
import com.mobikan.ks.model.Position;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import static com.mobikan.ks.utils.Constants.RatingDialog.BUNDLE_POSITION_ID;
import static com.mobikan.ks.utils.Constants.RatingDialog.BUNDLE_POSITION_IMAGE_ID;

public class PositionDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private int imageId;
    private Position position;
    private ActivityDetailBinding binding;
    private PositionDataBaseHelper positionDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail );
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          position = getIntent().getParcelableExtra("position_data");


        toolbar.setTitle(position.getTitle());
        setTitle(position.getTitle());
        //supportPostponeEnterTransition();
        init();
        findViewById(R.id.radialMenu).setOnClickListener(this);

    }


    private void init()
    {

        ImageView imageView = findViewById(R.id.imageView);
        TextView benefits = findViewById(R.id.benefits);
        TextView details = findViewById(R.id.details);


        benefits.setText(position.getBenefits());
        details.setText(position.getDescription());


        binding.title.setOnClickListener(this);
        binding.toolbarLayout.setOnClickListener(this);
        binding.imageView.setOnClickListener(this);

        imageId = getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", getPackageName());
        imageView.setImageResource(R.drawable.test);
        //imageView.setVisibility(View.INVISIBLE);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));

        setTabLayout();

        setTriedPosition();

        setBookmark();

        setLike();
    }

    private void setTabLayout()
    {
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager =  findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(position));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tips_his);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tips_her);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_try_this);
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
                    positionDataBaseHelper.setTried(position);
                }
                else
                {
                    check.check();
                    position.setTried(true);
                    positionDataBaseHelper.setTried(position);
                }
            }
        });
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

                }
                position.setFavourite(buttonState);
                positionDataBaseHelper.setFavourite(position);
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

        final ToggleButton toggleButton = findViewById(R.id.likeButton);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position.isLiked())
                {
                    position.setLiked(false);
                    toggleButton.setChecked(false);
                    FireBaseQueries.getInstance().resetLike(FireBaseQueries.LIKE_POSITION, position.getId());
                    positionDataBaseHelper.setLiked(position);
                    return;
                }
                else
                {
                    position.setLiked(true);
                    toggleButton.setChecked(true);
                    FireBaseQueries.getInstance().updateLike(FireBaseQueries.LIKE_POSITION, position.getId());
                    positionDataBaseHelper.setLiked(position);
                }

            }
        });

        final TextView likeCount = findViewById(R.id.likeCount);
        FireBaseQueries.getInstance().readLikeById(FireBaseQueries.LIKE_POSITION, position.getId(), new ValueEventListener() {
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
            case R.id.addToReminder:
                startActivity(CalendarHelper.onAddEventClicked(position));
                break;



            case R.id.rating:
                if(position.getRating()== 0) {
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
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        positionDataBaseHelper.closeDb();
    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
