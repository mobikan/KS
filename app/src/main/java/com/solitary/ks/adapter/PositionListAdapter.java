package com.solitary.ks.adapter;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


import com.solitary.ks.R;
import com.solitary.ks.utils.Utils;
import com.solitary.ks.listener.PositionClickListener;
import com.solitary.ks.model.Position;

public class PositionListAdapter extends RecyclerView.Adapter<PositionListAdapter.ViewHolder> {
    private List<Position> mDataset;

    private PositionClickListener onClickListener;

    private View.OnClickListener onViewClickListener;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public void setOnClickListener(PositionClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public void setPositions(List<Position> positions) {
        this.mDataset = positions;
        notifyDataSetChanged();
    }

    public List<Position> getAllPositions()
    {
        return mDataset;
    }

    public void setOnCheckedChangeListener(View.OnClickListener onViewClickListener)
    {
        this.onViewClickListener = onViewClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView positionName;
        public View itemLayout;
        public ImageView positionIcon;
        public RatingBar ratingBar;
        public ToggleButton favouriteButton;
        public ViewHolder(View v) {
            super(v);
            itemLayout = v;
            positionName = v.findViewById(R.id.positionName);
            positionIcon =  v.findViewById(R.id.image_view);
            ratingBar = v.findViewById(R.id.rating);
            favouriteButton =  v.findViewById(R.id.button_favorite);
            favouriteButton.setBackgroundResource(R.drawable.favourite_selector);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PositionListAdapter(ArrayList<Position> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PositionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        bind(holder,mDataset.get(position), position);

    }

    public void bind(final ViewHolder holder, final Position position, final int index) {

        holder.positionName.setText(position.getTitle());
        holder.ratingBar.setRating(position.getRating());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop().override(100, 100);
        int imageId = holder.itemLayout.getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", holder.itemLayout.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(R.drawable.app_icon)
                .apply(requestOptions)
                .into(holder.positionIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                v.setTag(position);

                if(onClickListener != null) {
                    onClickListener.onItemClick(position, holder.positionIcon);
                }
            }
        });

        if(position.isFavourite())
        {
            holder.favouriteButton.setChecked(true);
        }
        else {
            holder.favouriteButton.setChecked(false);
        }


            holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position.isFavourite()){
                        position.setFavourite(false);
                    }
                    else {
                        position.setFavourite(true);
                    }

                    mDataset.set(index, position);
                    holder.favouriteButton.setTag(position);
                    //notifyItemChanged(index);
                    if(onViewClickListener != null) {
                        onViewClickListener.onClick(holder.favouriteButton);
                    }
                }
            }
            );



    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}