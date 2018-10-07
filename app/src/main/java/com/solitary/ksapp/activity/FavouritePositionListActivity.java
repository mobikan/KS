package com.solitary.ksapp.activity;

import android.view.View;

import com.solitary.ksapp.R;
import com.solitary.ksapp.db.KSDatabaseHelper;
import com.solitary.ksapp.db.PositionDataBaseHelper;

public class FavouritePositionListActivity extends PositionListActivity {



    protected void setTitle()
    {
        binding.toolbar.setTitle(R.string.title_favourite_position);
    }

    protected void readDataFromDB()
    {
        try {
            PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(KSDatabaseHelper.getInstance(this));
            positionListAdapter.setPositions(positionDataBaseHelper.getAllFavouritePositions());
            positionListAdapter.setOnCheckedChangeListener(this);
            if(positionListAdapter.getAllPositions().size() == 0 )
            {
                binding.noDataText.setVisibility(View.VISIBLE);
                binding.noDataText.setText(R.string.no_data_favourite);
            }
            else
            {
                binding.noDataText.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
