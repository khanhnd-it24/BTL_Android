package com.example.appfoodorder.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String uid;
    private String catUid;
    private String title;
    private String pic;
    private String description;
    private Double price;
    private Boolean isPopular;
    private int numberInCart;

    public Food() {
    }

    public Food(String uid, String catUid, String title, String pic, String description, Double price, Boolean isPopular) {
        this.uid = uid;
        this.catUid = catUid;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.isPopular = isPopular;
    }

    public Food(String catUid, String title, String pic, String description, Double price, Boolean isPopular) {
        this.catUid = catUid;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.isPopular = isPopular;
    }

    public Food( String title, String pic, String description, Double price, Boolean isPopular) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.isPopular = isPopular;
    }

    public Food(String catUid, String title, String pic, String description, Double price, Boolean isPopular, int numberInCart) {
        this.catUid = catUid;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.isPopular = isPopular;
        this.numberInCart = numberInCart;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCatUid() {
        return catUid;
    }

    public void setCatUid(String catUid) {
        this.catUid = catUid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }
}
