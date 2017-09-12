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

        MyProfileFragment myProfileFragment = new MyProfileFragment();
        myProfileFragment.setMainActivity(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content , myProfileFragment, "MyProfile");
        ft.commit();

        navigation.setSelectedItemId(R.id.navigation_profile);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Tap back again to exit", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            back_pressed = System.currentTimeMillis();
        }

    }

}
