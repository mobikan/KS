package com.solitary.ks.activity;

import android.view.View;

import com.solitary.ks.R;
import com.solitary.ks.db.PositionDataBaseHelper;
import com.solitary.ks.db.KSDatabaseHelper;

public class TriedPositionsList extends PositionListActivity {



    protected void readDataFromDB()
    {
        try {
            PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(KSDatabaseHelper.getInstance(this));
            positionListAdapter.setPositions(positionDataBaseHelper.getAllTriedPositions());
            positionListAdapter.setOnCheckedChangeListener(this);
            if(positionListAdapter.getAllPositions().size() == 0 )
            {
                binding.noDataText.setVisibility(View.VISIBLE);
                binding.noDataText.setText(R.string.no_data_tried);
            }
            else
            {
                binding.noDataText.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void setTitle()
    {
        binding.toolbar.setTitle(R.string.title_tried_position);
    }


}
