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

public class UserComplaintFragment extends Fragment {

    RecyclerView rv;
    ArrayList<String> list = new ArrayList<>();
    UserComplaintListAdapter adapter;

    public UserComplaintFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user_complaint, container, false);

        list.add("Title1");
        list.add("Title2");
        list.add("Title3");
        list.add("Title4");
        list.add("Title5");
        list.add("Title6");
        list.add("Title7");
        list.add("Title8");
        list.add("Title9");
        list.add("Title10");
        list.add("Title11");
        list.add("Title12");
        list.add("Title13");
        list.add("Title14");
        list.add("Title15");
        adapter = new UserComplaintListAdapter();
        adapter.setList(list);
        adapter.setUserComplaintFragment(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv = (RecyclerView) rootview.findViewById(R.id.post_list);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        return rootview;
    }
}
