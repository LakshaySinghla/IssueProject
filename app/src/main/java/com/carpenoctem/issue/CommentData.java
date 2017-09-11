package com.carpenoctem.issue;

/**
 * Created by Lakshay Singhla on 11-Sep-17.
 */

public class CommentData {
    private String id, body, userId, complaintId, date;

    public void setId(String id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getDate() {
        return date;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }
}
