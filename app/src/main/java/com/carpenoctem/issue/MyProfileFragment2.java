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

public class MyProfileFragment2 extends Fragment {

    MainActivity mainActivity;

    public static MyProfileFragment2 newFragment(){
        return new MyProfileFragment2();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_my_profile_two, container, false);
        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("hi");
        mainActivity.setSupportActionBar(tb);

        ViewPager pager = rootview.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootview.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        Viewpageradapter adapter = new Viewpageradapter(mainActivity.getSupportFragmentManager());
        pager.setAdapter(adapter);

        return rootview;
    }

    public class Viewpageradapter extends FragmentPagerAdapter {
        String tabTitle[] = new String[] {"About","Post","Comment"};

        public Viewpageradapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:return new AboutFragment();
                case 1:return new PostFragment();
                case 2:return new CommentFragment();
                default:return new AboutFragment();
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
