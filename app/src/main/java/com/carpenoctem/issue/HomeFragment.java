package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    RecyclerView rv;
    HomeListAdapter adapter;
    ArrayList<String> list = new ArrayList<>();

    static HomeFragment newFragment(){
        return new HomeFragment();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("Home");

        list.add("Title 1");
        list.add("Title 2");
        list.add("Title 3");
        list.add("Title 4");
        list.add("Title 5");
        list.add("Title 6");
        list.add("Title 7");
        list.add("Title 8");
        list.add("Title 9");
        list.add("Title 10");
        list.add("Title 11");
        list.add("Title 12");

        adapter = new HomeListAdapter();
        adapter.setList(list);
        adapter.setHomeFragment(this);

        rv = (RecyclerView) rootview.findViewById(R.id.home_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        mainActivity.setSupportActionBar(tb);
        return rootview;
    }
}
