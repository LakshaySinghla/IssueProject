package com.carpenoctem.issue;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class CompleteComplaintActivity extends AppCompatActivity {

    RecyclerView rv;
    CompleteComplaintAdapter adapter;
    private String complaintId;
    RequestQueue queue;

    ComplaintData complaintData = new ComplaintData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_complaint);

        complaintId = getIntent().getExtras().getString("complaint_id");
        getComplaintData();

        adapter = new CompleteComplaintAdapter();
        adapter.setComplaintData(complaintData);

        rv = (RecyclerView) findViewById(R.id.comments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private void getComplaintData(){
        String url = AppUrls.Url_Complaints + "/" + complaintId ;
        queue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lakshay", response.toString() );
                        try{
                            JSONObject data = response.getJSONObject("data");
                            JSONObject j1 = data.getJSONObject("attributes");
                            complaintData.setId( data.getString("id") );
                            complaintData.setTitle( j1.getString("title") );
                            complaintData.setDescription( j1.getString("description") );

                            j1 = data.getJSONObject("relationships");
                            JSONObject j2 = j1.getJSONObject("user");
                            JSONObject j3 = j2.getJSONObject("data");
                            complaintData.setUserId(j3.getString("id") );
                            j2 = j1.getJSONObject("comments");
                            JSONArray cdata = j2.getJSONArray("data");
                            ArrayList<CommentData> commentDataArrayList = new ArrayList<>();
                            for(int i=0 ; i<cdata.length(); i++){
                                CommentData c = new CommentData();
                                j3 = cdata.getJSONObject(i);
                                c.setId( j3.getString("id") );
                                c.setBody( j3.getString("body") );
                                c.setUserId( j3.getString("user-id") );
                                c.setComplaintId( j3.getString("complaint-id") );
                                c.setDate( j3.getString("created-at") );

                                commentDataArrayList.add(c);
                            }
                            complaintData.setList(commentDataArrayList);
                            adapter.setComplaintData(complaintData);
                            adapter.setCompleteComplaintActivity(CompleteComplaintActivity.this);
                            adapter.notifyDataSetChanged();
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

    void performComment(String id, String body){
        queue = Volley.newRequestQueue(this);
        String url = AppUrls.Url_Complaints + "/" + id + "/comments";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        try {
            j1.put("body", body );
            j2.put("comment", j1);
        }
        catch (JSONException e){
            e.getStackTrace();
        }
        Log.v("Sent Post Comment", j2.toString() );

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url , j2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lak Post Comment", response.toString() );
                        getComplaintData();
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("Lakshay","MainActivity Inside getpreference, "+key+": "+ preferences.getString(key, null));
        return preferences.getString(key, null);
    }
}
