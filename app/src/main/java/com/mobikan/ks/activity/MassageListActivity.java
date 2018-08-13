package com.mobikan.ks.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.mobikan.ks.R;
import com.mobikan.ks.adapter.PageAdapter;
import com.mobikan.ks.databinding.MassageListScreenBinding;
import com.mobikan.ks.db.DataBaseHelper;
import com.mobikan.ks.db.MassageDataBaseHelper;
import com.mobikan.ks.firebase.FireBaseQueries;
import com.mobikan.ks.listener.PositionClickListener;
import com.mobikan.ks.model.Kiss;
import com.mobikan.ks.model.Massage;
import com.mobikan.ks.model.Position;

import static com.mobikan.ks.db.DataBaseHelper.DB_NAME_MASSAGE;


public class MassageListActivity extends AppCompatActivity  implements PositionClickListener<Kiss> {


    private MassageListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.massage_list_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init()
    {
//       // binding.recyclerView.setVisibility(View.INVISIBLE);
//        setSupportActionBar(binding.toolbar);
//        binding.toolbar.setTitle("Massage");
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        int margin =  getResources().getDimensionPixelOffset(R.dimen.recycler_view_margin);
//        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(margin,margin,margin,margin);
//        binding.recyclerView.addItemDecoration(itemOffsetDecoration);
        ArrayList<Kiss> kissArrayList = new ArrayList<>();


        pageAdapter = new PageAdapter(getSupportFragmentManager());

        readDataFromDb();
        binding.materialViewPager.getViewPager().setAdapter(pageAdapter);
        binding.materialViewPager.getPagerTitleStrip().setViewPager(binding.materialViewPager.getViewPager());
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

    private void readDataFromDb()
    {
        try {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
            MassageDataBaseHelper massageDataBaseHelper = new MassageDataBaseHelper(dataBaseHelper.openDatabase(DB_NAME_MASSAGE));
            List<Massage> massageArrayList = massageDataBaseHelper.getAllMassage();
            pageAdapter.setMassageList(massageArrayList);



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
