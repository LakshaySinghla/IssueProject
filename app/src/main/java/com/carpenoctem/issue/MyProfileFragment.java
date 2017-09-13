package com.carpenoctem.issue;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v4.app.FragmentTransaction;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    RequestQueue queue;

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
        location = (TextView) rootview.findViewById(R.id.location);
        name = (TextView) rootview.findViewById(R.id.name);

        pager = rootview.findViewById(R.id.view_pager);
        tabLayout = rootview.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        //if(mainActivity.userData == null){
            getUserData();
        //}else{
        //    location.setText("Location:"+ mainActivity.userData.getLocation() );
        //    name.setText( mainActivity.userData.getName() );
        //    adapter = new Viewpageradapter(getChildFragmentManager());
        //    pager.setAdapter(adapter);
        //}

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
                    userComplaintFragment.setName(mainActivity.userData.getName());
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

    void getUserData(){
        String url = AppUrls.Url_UserData + getPreference("user_id");
        queue = Volley.newRequestQueue(mainActivity);
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lakshay", response.toString() );
                        try{
                            mainActivity.userData = new UserData();
                            JSONObject data = response.getJSONObject("data");
                            JSONObject json1 = new JSONObject();
                            mainActivity.userData.setId( data.getString("id") );
                            json1 = data.getJSONObject("attributes");
                            mainActivity.userData.setName( json1.getString("full-name") );
                            mainActivity.userData.setEmail( json1.getString("email") );
                            mainActivity.userData.setLocation( json1.getString("state") );

                            JSONArray jsonArray = json1.getJSONArray("user-complaints");
                            ArrayList<ComplaintData> complaintList = new ArrayList<>();
                            for(int i=0; i< jsonArray.length() ; i++){
                                JSONObject j1 = jsonArray.getJSONObject(i);
                                ComplaintData c = new ComplaintData();
                                c.setId( j1.getString("id") );
                                c.setTitle( j1.getString("title") );
                                c.setDescription( j1.getString("description") );
                                c.setUserId(data.getString("id") );
                                complaintList.add(c);
                            }
                            mainActivity.userData.setComplaintList(complaintList);

                            json1 = data.getJSONObject("relationships");
                            JSONObject json2 = json1.getJSONObject("comments");
                            jsonArray = json2.getJSONArray("data");
                            ArrayList<CommentData> commentList = new ArrayList<>();
                            for(int i=0; i<jsonArray.length() ; i++){
                                JSONObject j1 = jsonArray.getJSONObject(i);
                                CommentData c =new CommentData();
                                c.setId( j1.getString("id") );
                                c.setBody( j1.getString("body") );
                                c.setUserId( j1.getString("user-id") );
                                c.setComplaintId( j1.getString("complaint-id") );
                                c.setDate( j1.getString("created-at") );
                                commentList.add(c);
                            }
                            mainActivity.userData.setCommentList(commentList);

                            location.setText("Location:"+ mainActivity.userData.getLocation() );
                            name.setText( mainActivity.userData.getName() );

                            adapter = new Viewpageradapter(getChildFragmentManager());
                            pager.setAdapter(adapter);

                        }
                        catch (JSONException e){
                            Log.v("JsonError",e.toString());
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Lakshay","Error Response: "+error.toString());
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =  new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put( "Authorization",getPreference("auth") );
                return params;
            }
        };
        queue.add(jsonRequest);
    }

    public String getPreference(String key ){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        Log.v("Lakshay","MainActivity Inside getpreference, "+key+": "+ preferences.getString(key, null));
        return preferences.getString(key, null);
    }

}
