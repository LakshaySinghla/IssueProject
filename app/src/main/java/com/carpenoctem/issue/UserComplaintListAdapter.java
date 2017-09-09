package com.carpenoctem.issue;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class UserComplaintListAdapter extends RecyclerView.Adapter<UserComplaintListAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(userComplaintFragment.getContext(),CompleteComplaintActivity.class);
                    userComplaintFragment.getActivity().startActivity(i);
                }
            });
        }
    }

    ArrayList<String> list = new ArrayList<>();
    UserComplaintFragment userComplaintFragment;

    void setList(ArrayList list){
        this.list = list;
    }

    public void setUserComplaintFragment(UserComplaintFragment userComplaintFragment) {
        this.userComplaintFragment = userComplaintFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_compliant_item_view, parent, false);
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
