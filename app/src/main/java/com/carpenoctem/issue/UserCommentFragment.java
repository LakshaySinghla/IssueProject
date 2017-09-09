package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class UserCommentFragment extends Fragment {

    RecyclerView rv;
    UserCommentListAdapter adapter;
    ArrayList<String> list = new ArrayList<>();

    public UserCommentFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user_comment, container, false);

        list.add("Comment 1");
        list.add("Comment 2");
        list.add("Comment 3");
        list.add("Comment 4");
        list.add("Comment 5");
        list.add("Comment 6");
        list.add("Comment 7");
        list.add("Comment 8");
        list.add("Comment 9");
        list.add("Comment 10");
        list.add("Comment 11");
        list.add("Comment 12");
        list.add("Comment 13");
        list.add("Comment 14");
        adapter = new UserCommentListAdapter();
        adapter.setList(list);
        adapter.setUserCommentFragment(this);
        rv = (RecyclerView) rootview.findViewById(R.id.comment_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        return rootview;
    }
}
