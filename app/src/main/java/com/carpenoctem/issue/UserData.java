package com.carpenoctem.issue;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */

public class UserData implements Serializable {
    private String name, email, location = "Delhi", id;
    private ArrayList<ComplaintData> complaintList = new ArrayList<>();
    private ArrayList<CommentData> commentList = new ArrayList<>();

    UserData(){

    }

    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setComplaintList(ArrayList<ComplaintData> complaintList) {
        this.complaintList = complaintList;
    }
    public void setCommentList(ArrayList<CommentData> commentList) {
        this.commentList = commentList;
    }


    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public ArrayList<ComplaintData> getComplaintList() {
        return complaintList;
    }
    public ArrayList<CommentData> getCommentList() {
        return commentList;
    }

}
