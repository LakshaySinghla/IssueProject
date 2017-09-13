package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class UserComplaintFragment extends Fragment {

    RecyclerView rv;
    ImageView empty;
    ArrayList<ComplaintData> list = new ArrayList<>();
    String name;
    UserComplaintListAdapter adapter;


    public UserComplaintFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user_complaint, container, false);
        rv = (RecyclerView) rootview.findViewById(R.id.post_list);
        empty = (ImageView) rootview.findViewById(R.id.empty);

        if(list.size() > 0) {
            empty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            adapter = new UserComplaintListAdapter();
            adapter.setList(list);
            adapter.setUserName(name);
            adapter.setUserComplaintFragment(this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
        }
        else{
            rv.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        return rootview;
    }

    void setList(ArrayList<ComplaintData> list){
        this.list = list;
    }
    void setName(String name){
        this.name = name;
    }

}
