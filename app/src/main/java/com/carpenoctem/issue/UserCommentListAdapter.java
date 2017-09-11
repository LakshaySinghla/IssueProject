package com.carpenoctem.issue;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class UserCommentListAdapter extends RecyclerView.Adapter<UserCommentListAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView comment;
        public MyViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.comment);


            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(userCommentFragment.getContext(),CompleteComplaintActivity.class);
                    userCommentFragment.getActivity().startActivity(i);
                }
            });
        }
    }

    ArrayList<CommentData> list = new ArrayList<>();
    UserCommentFragment userCommentFragment;

    void setList(ArrayList<CommentData> list){
        this.list = list;
    }
    public void setUserCommentFragment(UserCommentFragment userCommentFragment) {
        this.userCommentFragment = userCommentFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_comment_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.comment.setText("Body: "+list.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
