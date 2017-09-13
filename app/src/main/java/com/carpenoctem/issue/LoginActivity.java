package com.carpenoctem.issue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputEmail, inputPassword;
    Button login, signup;
    private String email,password, auth;
    double back_pressed;
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getPreference("auth") != null && getPreference("user_id") != null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);
        signup = (Button) findViewById(R.id.btn_signup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            if(checkEmmail() && checkPassword())
                executeLogin();
        }
        else if(view.getId() == R.id.btn_signup){
            Intent i = new Intent(this,SignupActivity.class);
            startActivity(i);
            finish();
        }
    }

    private boolean checkEmmail(){
        if(email.contains("@") && email.contains("."))
            return true;
        else{
            inputEmail.setError("Invalid Id");
            return false;
        }
    }
    private boolean checkPassword(){
        if(password.length() >= 8)
            return true;
        else {
            inputPassword.setError("Too small password");
            return false;
        }
    }

    private void executeLogin(){
        queue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        try{
            json.put("email",email);
            json.put("password",password);
            jsonObject.put("auth",json);
        }
        catch (JSONException e){
            e.getStackTrace();
        }
        Log.v("Lakshay","Response Sent: " + jsonObject.toString() );

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppUrls.Url_Login, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lakshay:Success ","Response: " + response.toString() );
                        try {
                            auth = response.getString("jwt");

                            String[] split = auth.split("\\.");
                            byte[] decodedBytes = Base64.decode(split[1], Base64.URL_SAFE);
                            JSONObject json = new JSONObject(new String(decodedBytes, "UTF-8"));
                            String id = json.getString("sub");
                            setPreference("user_id",id);

                            auth = "Bearer " + auth ;
                            setPreference("auth", auth);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        catch (JSONException e){
                            Log.v("Lakshay","Error");
                        }
                        catch (UnsupportedEncodingException e){
                            Log.v("Lakshay","Didn't work Base64");
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Lakshay","Error: "+error.toString() ) ;
                        Toast.makeText(LoginActivity.this,"Something went wrong\nTry Again",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =  new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
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

    private void setPreference(String key , String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).apply();
    }

    public String getPreference(String key ){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("Lakshay","Login: Inside getpreference, "+key+": "+ preferences.getString(key, null));
        return preferences.getString(key, null);
    }

}
