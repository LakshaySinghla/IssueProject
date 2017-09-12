package com.carpenoctem.issue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class ComplaintAdapter extends RecyclerView.Adapter {

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, date, detail;
        public ListViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.dp);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            detail = (TextView) itemView.findViewById(R.id.comment);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title, name, date, description, share, comment;
        ImageView img, up, down;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            description = (TextView) itemView.findViewById(R.id.description);
            comment = (TextView) itemView.findViewById(R.id.write_comment);
            share = (TextView) itemView.findViewById(R.id.share);
            img = (ImageView) itemView.findViewById(R.id.dp);
            up = (ImageView) itemView.findViewById(R.id.upvote);
            down = (ImageView) itemView.findViewById(R.id.downvote);
        }
    }

    private ArrayList<String> list;
    private int HView = 0, LView = 1;

    void setList(ArrayList<String> list){
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == LView) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item_view, parent, false);
            return new ListViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_header_view, parent, false);
            return new HeaderViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).detail.setText(list.get(position-1));
        }
        else{

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HView;
        }
        else return LView;
    }

    @Override
    public int getItemCount() {
        return (list.size()+1);
    }
}
