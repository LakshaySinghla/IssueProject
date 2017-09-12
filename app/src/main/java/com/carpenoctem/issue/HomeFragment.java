package com.carpenoctem.issue;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    RecyclerView rv;
    HomeListAdapter adapter;
    //ArrayList<String> list = new ArrayList<>();
    ArrayList<ComplaintData> complaintList = new ArrayList<>();
    RequestQueue queue;

    static HomeFragment newFragment(){
        return new HomeFragment();
    }
    void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar tb = rootview.findViewById(R.id.tool_bar);
        tb.setTitle("Home");
        getData();

        adapter = new HomeListAdapter();
        adapter.setList(complaintList);
        adapter.setHomeFragment(this);

        rv = (RecyclerView) rootview.findViewById(R.id.home_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        mainActivity.setSupportActionBar(tb);
        return rootview;
    }

    void getData(){
        complaintList.clear();
        queue = Volley.newRequestQueue(mainActivity);
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, AppUrls.Url_Complaints, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lakshay HOME", response.toString() );
                        try{
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                ComplaintData c = new ComplaintData();
                                JSONObject j1 = data.getJSONObject(i);
                                JSONObject j2 = j1.getJSONObject("attributes");
                                c.setId( j1.getString("id"));
                                c.setTitle( j2.getString("title"));
                                c.setDescription( j2.getString("description"));

                                j2 = j1.getJSONObject("relationships");
                                JSONObject j3 = j2.getJSONObject("user");
                                JSONObject j4 = j3.getJSONObject("data");
                                c.setUserId( j4.getString("id") );

                                j3 = j2.getJSONObject("comments");
                                JSONArray j5 = j3.getJSONArray("data");
                                ArrayList<CommentData> commentlist = new ArrayList<>();
                                for(int j=0; j< j5.length() ; j++ ){
                                    j4 = j5.getJSONObject(j);
                                    CommentData comment = new CommentData();
                                    comment.setId( j4.getString("id") );
                                    comment.setBody( j4.getString("body"));
                                    comment.setUserId( j4.getString("user-id") );
                                    comment.setDate( j4.getString("created-at") );
                                    commentlist.add(comment);
                                }
                                c.setList(commentlist);
                                complaintList.add(c);
                            }

                            adapter.setList(complaintList);
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

    void performComplaint(String title, String desc){
        queue = Volley.newRequestQueue(mainActivity);
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        try {
            j1.put("title", title );
            j1.put("description", desc);
            j2.put("complaint", j1);
        }
        catch (JSONException e){
            e.getStackTrace();
        }
        Log.v("Sent Post Complaint", j2.toString() );
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppUrls.Url_Complaints , j2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Lak Post Complaint", response.toString() );
                        getData();

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

    void performComment(String id, String desc, final int pos){
        queue = Volley.newRequestQueue(mainActivity);
        String url = AppUrls.Url_Complaints + "/" + id + "/comments";
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        try {
            j1.put("body", desc );
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
                        try{
                            CommentData commentData = new CommentData();
                            ArrayList<CommentData> l = complaintList.get(pos).getList();
                            JSONObject data = response.getJSONObject("data");
                            commentData.setComplaintId( data.getString("id") );
                            JSONObject relationships = data.getJSONObject("relationships");
                            JSONObject comments = relationships.getJSONObject("comments");
                            JSONArray cdata = comments.getJSONArray("data");
                            JSONObject c = cdata.getJSONObject(cdata.length()-1);
                            commentData.setId( c.getString("id") );
                            commentData.setBody( c.getString("body") );
                            commentData.setUserId( c.getString("user-id") );
                            commentData.setDate( c.getString("created-at") );

                            l.add(commentData);
                            complaintList.get(pos).setList(l);

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
        Log.v("Lakshay","Inside getpreference, "+key+": "+ preferences.getString(key, null));
        return preferences.getString(key, null);
    }
}
