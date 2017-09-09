package com.carpenoctem.issue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }

    ArrayList<String> list = new ArrayList<>();

    void setList(ArrayList list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
