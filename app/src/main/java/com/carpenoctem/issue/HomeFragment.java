package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class HomeFragment extends Fragment {

    MainActivity mainActivity;

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
        mainActivity.setSupportActionBar(tb);
        return rootview;
    }
}
