package com.solitary.ksapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;

import com.solitary.ksapp.R;

import com.solitary.ksapp.adapter.SpotListAdapter;
import com.solitary.ksapp.model.Spot;


/**
 * Created by Dev on 24/07/18.
 */
public class SpotListFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    public static final String BUNDLE_KEY_SPOT = "spot_list";


    private RecyclerView mRecyclerView;
    private ArrayList<Spot> spotList;

    public static SpotListFragment newInstance(Bundle bundle) {
        SpotListFragment spotListFragment =  new SpotListFragment();
        spotListFragment.setArguments(bundle);
        return spotListFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView =  view.findViewById(R.id.recyclerView);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            spotList = getArguments().getParcelableArrayList(BUNDLE_KEY_SPOT);
        }


        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(new SpotListAdapter(spotList));
    }
}