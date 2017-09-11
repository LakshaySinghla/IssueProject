package com.carpenoctem.issue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lakshay Singhla on 03-Sep-17.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mfname, mlname, mpassword, mcpassword, memail;
    Button signup_btn;
    TextView login_link;
    private RequestQueue queue ;
    double back_pressed;

    private String email, password, confirmPassword, name, auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        memail = (EditText) findViewById(R.id.input_email);
        mfname = (EditText) findViewById(R.id.input_first_name);
        mlname = (EditText) findViewById(R.id.input_last_name);
        mpassword = (EditText) findViewById(R.id.input_password);
        mcpassword = (EditText) findViewById(R.id.input_confirm_password);
        login_link = (TextView) findViewById(R.id.link_login);
        login_link.setOnClickListener(this);

        signup_btn = (Button) findViewById(R.id.btn_signup);
        signup_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                if(checkEmail() && checkPassword())
                    executeSignUp();
                break;
            case R.id.link_login:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            default:break;
        }
    }

    private boolean checkEmail(){
        email = memail.getText().toString();
        if(email.contains("@") && email.contains("."))
            return true;
        memail.setError("Invalid Email Id");
        return false;
    }

    private boolean checkPassword(){
        password = mpassword.getText().toString();
        confirmPassword = mcpassword.getText().toString();
        if(password.length() >= 8 && password.equals(confirmPassword) )
            return true;
        if(!(password.equals(confirmPassword)) )
            mcpassword.setError("Does not match with password");
        if(password.length() < 8)
            mpassword.setError("Too small Password");
        return false;
    }

    void executeSignUp(){
        queue = Volley.newRequestQueue(this);

        name = mfname.getText().toString() + " " + mlname.getText().toString();
        //String url = "https://young-falls-50132.herokuapp.com/api/v1/users";

        JSONObject jsonObject = new JSONObject();
        final JSONObject json = new JSONObject();
        //JSONObject jsonObject1= new JSONObject();
        try {
            //jsonObject.put("type","users");
            json.put("full_name", name);
            json.put("email",email);
            json.put("password",password);
            json.put("password_confirmation", confirmPassword);
            jsonObject.put("user",json);
            //jsonObject1.put("data",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("LAKSHAY","Rsponse Sent: "+jsonObject.toString());

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppUrls.Url_SignUp,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("Lakshay Success:", "Response obtained:" + response.toString());
                            Toast.makeText(SignupActivity.this, "Successfully Signed up",Toast.LENGTH_SHORT).show();
                            UserData userData = new UserData();
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

                            setPreference("user_id",userData.getId());
                            saveAuth();
                            Intent i = new Intent(SignupActivity.this, MainActivity.class);
                            i.putExtra("Data",userData);
                            startActivity(i);
                            finish();

                        }catch (Exception e){
                            Log.v("Error",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Lakshay", "Response Error:" + error.toString());
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

    private void saveAuth(){
        queue = Volley.newRequestQueue(this);

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
                            auth = "Bearer " + auth ;
                            setPreference("auth", auth);
                        }
                        catch (JSONException e){
                            e.getStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

    private void setPreference(String key , String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).apply();
        Log.v("Lakshay","Auth saved");
    }

}
