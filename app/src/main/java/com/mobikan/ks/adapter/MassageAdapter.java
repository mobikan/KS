package com.mobikan.ks.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.mobikan.ks.R;
import com.mobikan.ks.model.QnA;

/**
     * Created by Dev on 24/07/18.
     */
    public class MassageAdapter extends RecyclerView.Adapter<MassageAdapter.ViewHolder> {

        private List<QnA> qnAList;

        static final int TYPE_HEADER = 0;
        static final int TYPE_CELL = 1;

        public MassageAdapter(List<QnA> contents) {
            this.qnAList = contents;
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
            return qnAList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;

            switch (viewType) {
                case TYPE_HEADER: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.massage_list_item, parent, false);
                    return new ViewHolder(view) {
                    };
                }
                case TYPE_CELL: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.massage_list_item, parent, false);
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
            public TextView answers;

            public ViewHolder(View v) {
                super(v);
                itemLayout = v;
                titleName = v.findViewById(R.id.title);
                detail =  v.findViewById(R.id.detail);
                answers = v.findViewById(R.id.answers);

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
            bindView(holder, qnAList.get(position));
        }

        private void bindView(ViewHolder holder, QnA spot)
        {
            holder.titleName.setText(spot.getQuestion());

            setBulletPoint(spot.getAnswer(),holder);
        }


        private void setBulletPoint(ArrayList<String> answers,ViewHolder holder)
        {
            String htmlString = holder.titleName.getContext().getString(R.string.bullet_point);

            StringBuffer stringBuffer = new StringBuffer();


            for (int i = 0;i<answers.size();i++)
            {
               if(answers.size()== 1)
               {
                   stringBuffer.append(answers.get(i)+"\n");
               }
               else {

                   if(i > 0)
                   {
                       stringBuffer.append("\n");
                   }

                   stringBuffer.append(String.format(htmlString,answers.get(i)));

               }
            }
            holder.answers.setText(stringBuffer.toString());
        }
}
