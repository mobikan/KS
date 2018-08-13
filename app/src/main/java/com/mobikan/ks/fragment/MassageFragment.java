package com.mobikan.ks.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.mobikan.ks.adapter.MassageAdapter;
import com.mobikan.ks.model.Massage;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import com.mobikan.ks.R;
import com.mobikan.ks.model.QnA;


public class MassageFragment extends Fragment implements View.OnClickListener{

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView =  view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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


    @Override
    public void onClick(View view) {
        if(massage.getVideoLink() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(massage.getVideoLink())));
        }
    }
}
