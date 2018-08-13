package com.mobikan.ks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.mobikan.ks.R;
import com.mobikan.ks.model.Spot;

/**
 * Created by Dev on 24/07/18.
 */
public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.ViewHolder> {

    private List<Spot> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public SpotListAdapter(List<Spot> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public SpotListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new ViewHolder(view) {
                };
            }
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleName;
        public View itemLayout;

        public TextView detail;

        public ViewHolder(View v) {
            super(v);
            itemLayout = v;
            titleName = v.findViewById(R.id.title);
            detail =  v.findViewById(R.id.detail);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }
        bindView(holder,contents.get(position));
    }

    private void bindView(ViewHolder holder, Spot spot)
    {
        holder.titleName.setText(spot.getTitle());
        holder.detail.setText(spot.getDetail());
    }
}