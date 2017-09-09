package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class CompleteComplaintActivity extends AppCompatActivity {

    RecyclerView rv;
    ComplaintAdapter adapter;

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_complaint);

        list.add("Comment 1");
        list.add("Comment 2");
        list.add("Comment 3");
        list.add("Comment 4");
        list.add("Comment 5");
        list.add("Comment 6");
        list.add("Comment 7");
        list.add("Comment 8");
        list.add("Comment 9");
        adapter = new ComplaintAdapter();
        adapter.setList(list);

        rv = (RecyclerView) findViewById(R.id.comments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}
