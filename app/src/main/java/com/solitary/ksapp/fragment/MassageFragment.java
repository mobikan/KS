package com.solitary.ksapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solitary.ksapp.adapter.MassageAdapter;
import com.solitary.ksapp.model.Massage;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import com.solitary.ksapp.R;
import com.solitary.ksapp.model.QnA;


public class MassageFragment extends Fragment {

    private Massage massage;
    public static final String BUNDLE_KEY ="massage_key";
    private RecyclerView mRecyclerView;

    public static MassageFragment newInstance(Massage massage)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY, massage);
        MassageFragment massageFragment = new MassageFragment();
        massageFragment.setArguments(bundle);
      return massageFragment;
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
            massage = getArguments().getParcelable(BUNDLE_KEY);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());



        try {
            JSONObject jsonObject = new JSONObject(massage.getDetail());

            Type listType = new TypeToken<List<QnA>>() {}.getType();

            List<QnA> yourList = new Gson().fromJson(jsonObject.getJSONArray("qna").toString(), listType);

            mRecyclerView.setAdapter(new MassageAdapter(yourList));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    //@Override
    public void onClick(View view) {
        if(massage.getVideoLink() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(massage.getVideoLink())));
        }
    }
}
