package com.carpenoctem.issue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
