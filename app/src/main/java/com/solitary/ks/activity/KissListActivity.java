package com.solitary.ks.activity;

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

import java.util.ArrayList;
import java.util.List;

import com.solitary.ks.R;
import com.solitary.ks.adapter.KissListAdapter;
import com.solitary.ks.component.ItemOffsetDecoration;
import com.solitary.ks.databinding.KissListScreenBinding;
import com.solitary.ks.databinding.KsListScreenBinding;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.KissDataBaseHelper;
import com.solitary.ks.firebase.FireBaseQueries;
import com.solitary.ks.listener.PositionClickListener;
import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Massage;


public class KissListActivity extends AppCompatActivity  implements PositionClickListener<Kiss> {


    private KissListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    private KissListAdapter positionListAdapter;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.kiss_list_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

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

            KissDataBaseHelper kissDataBaseHelper = new KissDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_KISS));
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
    public void onItemClick(Kiss position, ImageView imageView) {
        Intent intent = new Intent(this,KissDetailActivity.class  );
        intent.putExtra(EXTRA_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));
        intent.putExtra("kiss_data", position);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView));

        startActivity(intent, options.toBundle());
    }
}
