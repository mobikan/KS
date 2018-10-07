package com.solitary.ksapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.solitary.ksapp.adapter.PageAdapter;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.MassageDataBaseHelper;
import com.solitary.ksapp.listener.PositionClickListener;
import com.solitary.ksapp.model.Kiss;
import com.solitary.ksapp.model.Massage;

import java.util.ArrayList;
import java.util.List;

import static com.solitary.ksapp.db.DataBaseHelper.DB_NAME_MASSAGE;


public class MassageListActivity extends AppCompatActivity  implements PositionClickListener<Kiss> {


   // private MassageListScreenBinding binding;
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "Image Transition";
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding =  DataBindingUtil.setContentView(this, R.layout.massage_list_screen);
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
       // binding.materialViewPager.getViewPager().setAdapter(pageAdapter);
       // binding.materialViewPager.getPagerTitleStrip().setViewPager(binding.materialViewPager.getViewPager());
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
