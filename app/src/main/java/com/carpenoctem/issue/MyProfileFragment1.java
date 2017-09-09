package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class MyProfileFragment1 extends Fragment {

    MainActivity mainActivity;
    RecyclerView recyclerView;
    MyProfileListviewAdapter adapter;
    ArrayList<String> about_list = new ArrayList<>();
    ArrayList<String> comment_list = new ArrayList<>();
    ArrayList<String> post_list = new ArrayList<>();

    public static MyProfileFragment1 newFragment(){
        return new MyProfileFragment1();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_my_profile_one, container, false);

        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("MyProfile");
        mainActivity.setSupportActionBar(tb);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview);

        adapter = new MyProfileListviewAdapter();
        adapter.setMyProfileFragment1(this);
        initialisAboutList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootview;
    }

    void initialisAboutList(){
        about_list.clear();
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        about_list.add("About Hii");
        adapter.setListType(MyProfileListviewAdapter.AboutView);
        adapter.setList(about_list);
        adapter.notifyDataSetChanged();
    }

    void initialiseCommentList(){
        comment_list.clear();
        comment_list.add("Comment Hii");
        comment_list.add("Comment Hii");
        comment_list.add("Comment Hii");
        comment_list.add("Comment Hii");
        comment_list.add("Comment Hii");
        comment_list.add("Comment Hii");
        adapter.setListType(MyProfileListviewAdapter.CommentView);
        adapter.setList(comment_list);
        adapter.notifyDataSetChanged();
    }

    void initialisePostList(){
        post_list.clear();
        post_list.add("Post Hii");
        post_list.add("Post Hii");
        post_list.add("Post Hii");
        post_list.add("Post Hii");
        post_list.add("Post Hii");
        adapter.setListType(MyProfileListviewAdapter.PostView);
        adapter.setList(post_list);
        adapter.notifyDataSetChanged();
    }
}
