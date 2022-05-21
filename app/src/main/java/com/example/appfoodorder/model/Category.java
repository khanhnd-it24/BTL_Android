package com.example.appfoodorder.model;

public class Category {
    private String uid;
    private String title;
    private String pic;

    public Category(String uid, String title, String pic) {
        this.uid = uid;
        this.title = title;
        this.pic = pic;
    }

    public Category() {
    }

    public Category(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
