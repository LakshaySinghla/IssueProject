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
    UserComplaintListAdapter adapter;

    public UserComplaintFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user_complaint, container, false);
        rv = (RecyclerView) rootview.findViewById(R.id.post_list);
        empty = (ImageView) rootview.findViewById(R.id.empty);

        ComplaintData c = new ComplaintData();
        c.setDescription("Description 1");
        c.setTitle("Title 1");
        c.setDate("Date 1");
        c.setByName("By Name 1");
        c.setId("Id 1");
        list.add(c);
        if(list.size() > 0) {
            empty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            adapter = new UserComplaintListAdapter();
            adapter.setList(list);
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

}
