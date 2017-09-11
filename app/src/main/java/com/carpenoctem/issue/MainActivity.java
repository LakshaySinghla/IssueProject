package com.carpenoctem.issue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity {

    double back_pressed=0;
    UserData userData = new UserData();
    RequestQueue queue;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment = null;
            Fragment h = getSupportFragmentManager().findFragmentByTag("Home");
            Fragment m = getSupportFragmentManager().findFragmentByTag("MyProfile");

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(!(h instanceof HomeFragment)) {
                        selectedfragment = HomeFragment.newFragment();
                        ((HomeFragment)selectedfragment).setMainActivity(MainActivity.this);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                        ft.replace(R.id.content, selectedfragment, "Home");
                        ft.commit();
                    }
                    break;
                case R.id.navigation_profile:
                    if(!(m instanceof MyProfileFragment)) {
                        selectedfragment = MyProfileFragment.newFragment();
                        ((MyProfileFragment) selectedfragment).setMainActivity(MainActivity.this);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                        ft.replace(R.id.content, selectedfragment, "MyProfile");
                        ft.commit();
                    }
                    break;
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        userData = (UserData) getIntent().getSerializableExtra("Data");
        if(userData == null){
            getUserData();
        }

        /*MyProfileFragment myProfileFragment = new MyProfileFragment();
        myProfileFragment.setMainActivity(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content , myProfileFragment, "MyProfile");
        ft.commit();
        */
        navigation.setSelectedItemId(R.id.navigation_profile);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),"Tap back again to exit", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    void getUserData(){
        String url = AppUrls.Url_UserData + getPreference("user_id");
        queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lakshay", response.toString() );
                        try{
                            userData = new UserData();
                            JSONObject data = response.getJSONObject("data");
                            JSONObject json1 = new JSONObject();
                            userData.setId( data.getString("id") );
                            json1 = data.getJSONObject("attributes");
                            userData.setName( json1.getString("full-name") );
                            userData.setEmail( json1.getString("email") );
                            userData.setLocation( json1.getString("state") );
                            json1 = data.getJSONObject("relationships");

                            JSONObject complaints = json1.getJSONObject("complaints");
                            JSONArray complaintArray = complaints.getJSONArray("data");
                            ArrayList<ComplaintData> complaintDataList = new ArrayList<>();

                            //Ye sab recheck karna hai, apne app se likh dia abhi bs
                            for(int i=0; i < complaintArray.length() ; i++){
                                JSONObject json2 = complaintArray.getJSONObject(i);
                                ComplaintData complaintData = new ComplaintData();
                                complaintData.setId( json2.getString("id") );
                                //complaintData.setByName( json2.getString("name") );
                                //complaintData.setDate( json2.getString("date") );
                                //complaintData.setTitle( json2.getString("title") );
                                //complaintData.setDescription( json2.getString("description") );
                                complaintDataList.add(complaintData);
                            }
                            userData.setComplaintList(complaintDataList);

                            JSONObject comments = json1.getJSONObject("comments");
                            JSONArray commentArray = comments.getJSONArray("data");
                            ArrayList<CommentData> commentList = new ArrayList<>();
                            for(int i=0; i < commentArray.length() ; i++){
                                JSONObject json2 = commentArray.getJSONObject(i);
                                CommentData commentData = new CommentData();
                                commentData.setId( json2.getString("id") );
                                commentData.setBody( json2.getString("body") );
                                commentData.setUserId( json2.getString("user-id") );
                                commentData.setComplaintId( json2.getString("complaint-id") );
                                commentData.setDate( json2.getString("created-at") );
                                commentList.add(commentData);
                            }
                            userData.setCommentList(commentList);
                        }
                        catch (JSONException e){
                            Log.v("JsonError",e.toString());
                        }

                        MyProfileFragment myProfileFragment = new MyProfileFragment();
                        myProfileFragment.setMainActivity(MainActivity.this);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content , myProfileFragment, "MyProfile");
                        ft.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Lakshay","Error Response: "+error.toString());

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("Lakshay","MainActivity Inside getpreference, "+key+": "+ preferences.getString(key, null));
        return preferences.getString(key, null);
    }
}
