package com.solitary.ksapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solitary.ksapp.R;
import com.solitary.ksapp.model.Position;

public class CustomPagerAdapter extends PagerAdapter {


    public CustomPagerAdapter(Position position) {

        data = new String[]{position.getTips_his(),position.getTips_her(),position.getTry_this()};
    }

    private String[] data= null;

    private String[] titleList= {"","",""};
    private int[] resourceIds= {R.drawable.ic_tips_his,R.drawable.ic_tips_her,R.drawable.ic_try_this};

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(collection.getContext());
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.detail_tips_layout, collection, false);
        TextView textView = layout.findViewById(R.id.textView);
        textView.setText(data[position]);
        TextView title = layout.findViewById(R.id.title);
        title.setText(titleList[position]);
        title.setCompoundDrawables(collection.getContext().getResources().getDrawable(R.drawable.ic_tips_her), null, null, null);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position)
        {
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
        }
        return title;
    }

}