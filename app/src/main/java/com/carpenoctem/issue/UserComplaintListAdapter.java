package com.carpenoctem.issue;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class UserComplaintListAdapter extends RecyclerView.Adapter<UserComplaintListAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, name, date, description;
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            name= (TextView) itemView.findViewById(R.id.name);
            date= (TextView) itemView.findViewById(R.id.date);
            description= (TextView) itemView.findViewById(R.id.description);
            img= (ImageView) itemView.findViewById(R.id.dp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(userComplaintFragment.getContext(),CompleteComplaintActivity.class);
                    i.putExtra( "complaint_id",list.get(getAdapterPosition()).getId() );
                    userComplaintFragment.getActivity().startActivity(i);
                }
            });

        }
    }

    ArrayList<ComplaintData> list = new ArrayList<>();
    String userName;
    UserComplaintFragment userComplaintFragment;

    void setList(ArrayList<ComplaintData> list){
        this.list = list;
    }
    void setUserName(String name){
        userName = name;
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
        holder.title.setText("Title: "+list.get(position).getTitle());
        holder.name.setText( "Name: "+ userName );
        holder.date.setText("Date: "+list.get(position).getDate());
        holder.description.setText("Description: " +list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
