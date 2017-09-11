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

public class HomeListAdapter extends RecyclerView.Adapter {
    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, name, description, share;
        ImageView img , up, down;
        public ListViewHolder(View itemView) {
            super(itemView);

            title= (TextView) itemView.findViewById(R.id.title);
            name= (TextView) itemView.findViewById(R.id.name);
            date= (TextView) itemView.findViewById(R.id.date);
            description= (TextView) itemView.findViewById(R.id.description);
            share= (TextView) itemView.findViewById(R.id.share);
            img= (ImageView) itemView.findViewById(R.id.dp);
            down= (ImageView) itemView.findViewById(R.id.downvote);
            up= (ImageView) itemView.findViewById(R.id.upvote);
        }
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView name,write;
        ImageView img;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            write = (TextView) itemView.findViewById(R.id.write);
            img = (ImageView) itemView.findViewById(R.id.dp);
        }
    }

    int HView =0 , LView =1;
    HomeFragment homeFragment;
    ArrayList<String> list;

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_view, parent, false);
            return new HeaderViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_view, parent, false);
            return new ListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){

        }
        else{
            ((ListViewHolder)holder).title.setText(list.get(position-1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return HView;
        else return LView;
    }

    @Override
    public int getItemCount() {
        return (list.size()+1);
    }
}
