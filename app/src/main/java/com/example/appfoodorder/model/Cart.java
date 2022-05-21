package com.example.appfoodorder.model;

import java.util.List;

public class Cart {
    private String userUid;
    private List<com.example.appfoodorder.model.Food> foods;

    public Cart(String userUid, List<com.example.appfoodorder.model.Food> foods) {
        this.userUid = userUid;
        this.foods = foods;
    }

    public Cart() {
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public List<com.example.appfoodorder.model.Food> getFoods() {
        return foods;
    }

    public void setFoods(List<com.example.appfoodorder.model.Food> foods) {
        this.foods = foods;
    }
}
