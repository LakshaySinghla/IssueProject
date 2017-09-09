package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class MyProfileFragment extends Fragment {

    MainActivity mainActivity;

    public static MyProfileFragment newFragment(){
        return new MyProfileFragment();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_my_profile, container, false);
        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("Name");
        mainActivity.setSupportActionBar(tb);

        ViewPager pager = rootview.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootview.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        Viewpageradapter adapter = new Viewpageradapter(mainActivity.getSupportFragmentManager());
        pager.setAdapter(adapter);

        return rootview;
    }

    public class Viewpageradapter extends FragmentPagerAdapter {
        String tabTitle[] = new String[] {"Complaints","Comment"};

        public Viewpageradapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:return new UserComplaintFragment();
                case 1:return new UserCommentFragment();
                default:return new UserComplaintFragment();
            }

        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }
}
