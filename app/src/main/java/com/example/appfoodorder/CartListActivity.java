package com.example.appfoodorder;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodorder.adapter.CartListAdapter;
import com.example.appfoodorder.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);


        initView();
        initList();

    }


    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList=findViewById(R.id.cartView);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();

        ArrayList<CartItem> cartItemList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("carts")
                .orderByChild("userUid").equalTo("BgJoJUQnMBZeu3MfXGYyZH7K34F3")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            FirebaseDatabase.getInstance().getReference("carts")
                                    .child(ds.getKey()).child("cartItems")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for( DataSnapshot dsx: snapshot.getChildren()) {
                                                CartItem cartItem = dsx.getValue(CartItem.class);
                                                cartItemList.add(cartItem);
                                            }
                                            adapter = new CartListAdapter(cartItemList,totalFeeTxt, taxTxt, deliveryTxt, totalTxt);
                                            recyclerViewList.setAdapter(adapter);
                                            if (cartItemList.isEmpty()) {
                                                emptyTxt.setVisibility(View.VISIBLE);
                                                scrollView.setVisibility(View.GONE);
                                            } else {
                                                emptyTxt.setVisibility(View.GONE);
                                                scrollView.setVisibility(View.VISIBLE);
                                                CalculateCart(cartItemList);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void CalculateCart(ArrayList<CartItem> cartItems) {
        double percentTax = 0.02;
        double delivery = 10;
        double totalPrice = 0;
        for(CartItem item: cartItems) {
            totalPrice += item.getFee() * item.getNumberInCart();
        }
        tax = Math.round((totalPrice * percentTax) * 100) / 100;
        double total = Math.round((totalPrice + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(totalPrice * 100) / 100;

        totalFeeTxt.setText("$" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delivery);
        totalTxt.setText("$" + total);
    }
}