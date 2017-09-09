package com.carpenoctem.issue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 08-Sep-17.
 */

public class MyProfileListviewAdapter extends RecyclerView.Adapter {

    private int HView = 0 ,listType;
    private ArrayList<String> dataList;
    public static int AboutView = 1, PostView =2 , CommentView =3;
    MyProfileFragment1 myProfileFragment1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_profile_header_view, parent, false);
            return new HeaderViewHolder(itemView);
        }
        else if (viewType == AboutView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_profile_about_view, parent, false);
            return new AboutListViewHolder(itemView);
        }
        else if (viewType == CommentView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_profile_comment_view, parent, false);
            return new CommentListViewHolder(itemView);
        }
        else if (viewType == PostView){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_profile_post_view, parent, false);
            return new PostListViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){

        }
        else if(holder instanceof AboutListViewHolder){
            ((AboutListViewHolder) holder).tv.setText(dataList.get(position-1));
        }
        else if(holder instanceof CommentListViewHolder){
            ((CommentListViewHolder) holder).tv.setText(dataList.get(position-1));
        }
        else if(holder instanceof PostListViewHolder){
            ((PostListViewHolder) holder).tv.setText(dataList.get(position-1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return HView;
        else if(listType == AboutView)
            return AboutView;
        else if (listType == CommentView)
            return CommentView;
        else return PostView;
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1 ;
    }

    public void setList(ArrayList<String> list){
        dataList = list;
    }

    public void setListType(int type){
        listType = type;
    }

    public void setMyProfileFragment1(MyProfileFragment1 myProfileFragment1){
        this.myProfileFragment1 = myProfileFragment1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name , about, post, comment;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.dp);
            name = (TextView ) itemView.findViewById(R.id.name);
            about = (TextView ) itemView.findViewById(R.id.about);
            post = (TextView ) itemView.findViewById(R.id.posts);
            comment = (TextView ) itemView.findViewById(R.id.comments);

            about.setActivated(true);

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myProfileFragment1.initialisAboutList();
                    about.setActivated(true);
                    post.setActivated(false);
                    comment.setActivated(false);
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myProfileFragment1.initialiseCommentList();
                    comment.setActivated(true);
                    about.setActivated(false);
                    post.setActivated(false);
                }
            });
            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myProfileFragment1.initialisePostList();
                    post.setActivated(true);
                    about.setActivated(false);
                    comment.setActivated(false);
                }
            });
        }
    }

    public class AboutListViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public AboutListViewHolder(View itemView) {
            super(itemView);
            tv =(TextView) itemView.findViewById(R.id.about_text);
        }
    }
    public class CommentListViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public CommentListViewHolder(View itemView) {
            super(itemView);
            tv =(TextView) itemView.findViewById(R.id.comment_text);
        }
    }
    public class PostListViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public PostListViewHolder(View itemView) {
            super(itemView);
            tv =(TextView) itemView.findViewById(R.id.post_text);
        }
    }
}
