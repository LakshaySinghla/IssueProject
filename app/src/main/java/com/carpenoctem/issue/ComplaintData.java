package com.carpenoctem.issue;


import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */
public class ComplaintData {

    private String id,title, byName, date, description, userId;
    private ArrayList<CommentData> list = new ArrayList<>();

    ComplaintData(){

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setList(ArrayList<CommentData> list) {
        this.list = list;
        Log.v("Lakshay","list Size: " + this.list.size() );
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setByName(String byName) {
        this.byName = byName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle() {
        return title;
    }
    public String getId() {
        return id;
    }
    public String getByName() {
        return byName;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<CommentData> getList() {
        return list;
    }

    public String getUserId() {
        return userId;
    }

}
