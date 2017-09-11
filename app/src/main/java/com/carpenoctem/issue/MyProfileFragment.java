package com.carpenoctem.issue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lakshay Singhla on 09-Sep-17.
 */

public class MyProfileFragment extends Fragment{

    MainActivity mainActivity;
    ViewPager pager;
    TabLayout tabLayout;
    Viewpageradapter adapter;
    Toolbar tb;
    ImageView menu;
    TextView location, name;

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

        tb = rootview.findViewById(R.id.tool_bar);
        mainActivity.setSupportActionBar(tb);

        pager = rootview.findViewById(R.id.view_pager);
        tabLayout = rootview.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        adapter = new Viewpageradapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        location = (TextView) rootview.findViewById(R.id.location);
        location.setText("Location:"+ mainActivity.userData.getLocation() );
        name = (TextView) rootview.findViewById(R.id.name);
        name.setText( mainActivity.userData.getName() );

        menu = (ImageView) rootview.findViewById(R.id.custom_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(mainActivity, menu);
                popup.getMenuInflater().inflate(R.menu.my_profile_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(mainActivity,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        if(item.getItemId() == R.id.settings){

                        }
                        else if(item.getItemId() == R.id.logout){
                            logout();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        return rootview;
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to logout?");
        // Add the buttons
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                executeLogout();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // Create the AlertDialog
        builder.create().show();
    }

    private void executeLogout(){
        clearDefaults(getContext());
        Intent intent = new Intent(mainActivity, LoginActivity.class);
        startActivity(intent);
        mainActivity.finish();
    }

    public static void clearDefaults(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    private class Viewpageradapter extends FragmentPagerAdapter {
        String tabTitle[] = new String[] {"Complaints","Comment"};

        public Viewpageradapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: UserComplaintFragment userComplaintFragment= new UserComplaintFragment();
                    userComplaintFragment.setList(mainActivity.userData.getComplaintList());
                    return userComplaintFragment;
                case 1: UserCommentFragment userCommentFragment = new UserCommentFragment();
                    userCommentFragment.setList(mainActivity.userData.getCommentList());
                    return userCommentFragment;
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
