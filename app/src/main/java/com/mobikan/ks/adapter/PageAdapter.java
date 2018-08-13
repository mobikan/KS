package com.mobikan.ks.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.mobikan.ks.fragment.MassageFragment;
import com.mobikan.ks.model.Massage;

public class PageAdapter extends FragmentPagerAdapter {
    public ArrayList<MassageFragment> fragmentArrayList = new ArrayList<>();
    private List<Massage> massageList;

    public MassageFragment getFragment(Massage massage)
    {
        MassageFragment massageFragment = new MassageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("massage_key", massage);
        massageFragment.setArguments(bundle);

    return massageFragment;
    }

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public MassageFragment getItem(int position) {
        //return MyFragment.newInstance();
        return getFragment(massageList.get(position));

    }



    @Override
    public int getCount() {
        // return CONTENT.length;
        return massageList ==  null ? 0:massageList.size();
    }

    public void setMassageList(List<Massage> massageList) {
        this.massageList = massageList;
    }
}