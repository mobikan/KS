package com.solitary.ksapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;


import com.solitary.ksapp.R;
import com.solitary.ksapp.utils.Utils;
import com.solitary.ksapp.listener.PositionClickListener;
import com.solitary.ksapp.model.Kiss;

public class KissListAdapter extends RecyclerView.Adapter<KissListAdapter.ViewHolder> {
    private List<Kiss> mDataset;

    private PositionClickListener<Kiss> onClickListener;

    public void setOnClickListener(PositionClickListener<Kiss> onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public void setPositions(List<Kiss> positions) {
        this.mDataset = positions;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleName;
        public View itemLayout;
        public ImageView kissImage;

        public ViewHolder(View v) {
            super(v);
            itemLayout = v;
            titleName = v.findViewById(R.id.kiss_name);
            kissImage =  v.findViewById(R.id.imageView);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public KissListAdapter(ArrayList<Kiss> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public KissListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kiss_layout_item, parent, false);

       // ViewHolder vh = new ViewHolder(v);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        bind(holder,mDataset.get(position));

    }



    public void bind(final ViewHolder holder, final Kiss position) {

        holder.titleName.setText(position.getTitle());
        int imageId = holder.itemLayout.getContext().getResources().getIdentifier(Utils.getImageName(position.getTitle()), "drawable", holder.itemLayout.getContext().getPackageName());


        Glide.with(holder.itemView.getContext()).load(imageId)

                .into(holder.kissImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                position.setImageId(Utils.getImageName(position.getTitle()));

                if(onClickListener != null) {

                    onClickListener.onItemClick(position, holder.kissImage);
                }
            }
        });


    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}