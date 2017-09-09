package com.carpenoctem.issue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rajiv Kumar on 03-Sep-17.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mfname, mlname, mpassword, mcpassword;
    Button signup_btn;
    RequestQueue queue ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfname = (EditText) findViewById(R.id.input_first_name);
        mlname = (EditText) findViewById(R.id.input_last_name);
        mpassword = (EditText) findViewById(R.id.input_password1);
        mcpassword = (EditText) findViewById(R.id.input_confirm_password);

        signup_btn = (Button) findViewById(R.id.btn_signup);
        signup_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                executeSignUp();
        }
    }

    void executeSignUp(){
        queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.11:3000/users";

        /**Map<String,String> params = new HashMap<String,String>();
        params.put("full_name",mfname.getText() + " " + mlname.getText());
        params.put("password",mpassword.getText().toString());
        params.put("password_confirmation", mcpassword.getText().toString());
        JSONObject jso = new JSONObject(params);
        params = new HashMap<>();
        params.put("type","users");
        //Map<String, JSONObject > params1 = new HashMap<>();
        //params1.put("attributes",jso);
        JSONObject jso1 = new JSONObject();
        try{
            jso1.put();
        }
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("auth",jso);
            Log.v("reponse I Sent",jsonObject1.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }**/

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        try {
            jsonObject.put("type","users");
            json.put("full_name",mfname.getText().toString() + " " + mlname.getText().toString());
            json.put("password",mpassword.getText().toString());
            json.put("password_confirmation", mcpassword.getText().toString());
            jsonObject.put("attributes",json);
            jsonObject1.put("data",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("LAKSHAY","Rsponse Sent: "+jsonObject.toString());

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("Lakshay", "Response obtained:" + response.toString());


                        }catch (Exception e){
                            Log.v("Error",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Lakshay","Response Error:" + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =  new HashMap<>();
                //if(params==null)params = new HashMap<>();
                params.put("Content-Type", "application/vnd.api+json");
                //..add other headers
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }
}
