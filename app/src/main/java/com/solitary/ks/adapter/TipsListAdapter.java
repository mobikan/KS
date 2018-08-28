package com.solitary.ks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.solitary.ks.R;
import com.solitary.ks.listener.PositionClickListener;
import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Tips;

import java.util.ArrayList;
import java.util.List;

import io.github.ponnamkarthik.richlinkpreview.RichLinkView;
import io.github.ponnamkarthik.richlinkpreview.RichLinkViewTwitter;
import io.github.ponnamkarthik.richlinkpreview.ViewListener;

public class TipsListAdapter extends RecyclerView.Adapter<TipsListAdapter.ViewHolder> {
    private List<Tips> tipsList;

    private PositionClickListener<Tips> onClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TipsListAdapter(ArrayList<Tips> myDataset) {
        tipsList = myDataset;
    }

    public void setOnClickListener(PositionClickListener<Tips> onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public void setPositions(List<Tips> positions) {
        this.tipsList = positions;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TipsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tips_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        bind(holder, tipsList.get(position));
    }

    public void bind(final ViewHolder holder, final Tips position) {
    holder.richLinkView.setLink(position.getUrl(), new ViewListener() {
        @Override
        public void onSuccess(boolean status) {

        }

        @Override
        public void onError(Exception e) {

        }
    });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ToggleButton likeButton;
        public View itemLayout;
        public TextView likeCount;
        public RichLinkViewTwitter richLinkView;

        public ViewHolder(View v) {
            super(v);
            itemLayout   = v;
            likeButton   = v.findViewById(R.id.likeToggleButton);
            likeCount    = v.findViewById(R.id.likeCount);
            richLinkView = v.findViewById(R.id.richLinkView);

        }
    }
}