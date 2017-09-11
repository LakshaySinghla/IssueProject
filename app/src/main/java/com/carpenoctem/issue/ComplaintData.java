package com.carpenoctem.issue;


/**
 * Created by Lakshay Singhla on 10-Sep-17.
 */
public class ComplaintData {

    private String id,title, byName, date, description;

    ComplaintData(){

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
}
