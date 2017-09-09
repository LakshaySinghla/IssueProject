package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment = null;
            Fragment h = getSupportFragmentManager().findFragmentByTag("Home");
            Fragment p = getSupportFragmentManager().findFragmentByTag("Popular");
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
                case R.id.navigation_popular:
                    if(!(p instanceof PopularFragment)) {
                        selectedfragment = PopularFragment.newFragment();
                        ((PopularFragment) selectedfragment).setMainActivity(MainActivity.this);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        if(h instanceof HomeFragment)
                            ft.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit);
                        else
                            ft.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit);
                        ft.replace(R.id.content, selectedfragment, "Popular");
                        ft.commit();
                    }
                    break;
                case R.id.navigation_profile:
                    if(!(m instanceof MyProfileFragment2)) {
                        selectedfragment = MyProfileFragment2.newFragment();
                        ((MyProfileFragment2) selectedfragment).setMainActivity(MainActivity.this);
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

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setMainActivity(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content , homeFragment, "Home");
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
