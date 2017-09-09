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

public class PopularFragment extends Fragment {

    MainActivity mainActivity;

    public static PopularFragment newFragment(){
        return new PopularFragment();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_popular, container, false);

        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("Popular");
        mainActivity.setSupportActionBar(tb);

        return rootview;
    }
}
