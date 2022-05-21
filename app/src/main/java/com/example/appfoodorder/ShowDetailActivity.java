package com.example.appfoodorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.appfoodorder.model.CartItem;
import com.example.appfoodorder.model.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Food object;
    int numberOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

//        managementCart = new ManagementCart(this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (Food) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("$" + object.getPrice());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder += 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder > 1) {
                    numberOrder -= 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String title, String pic, String description, Double fee, int numberInCart
                CartItem cartItem = new CartItem(object.getTitle(),object.getPic(), object.getDescription(), object.getPrice(),numberOrder);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                FirebaseUser user = mAuth.getCurrentUser();
                FirebaseDatabase.getInstance().getReference("carts")
                        .orderByChild("userUid").equalTo("BgJoJUQnMBZeu3MfXGYyZH7K34F3")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    FirebaseDatabase.getInstance().getReference("carts")
                                            .child(ds.getKey()).child("cartItems")
                                            .orderByChild("title").equalTo(cartItem.getTitle())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                                                    boolean isUpdate = false;
                                                    for(DataSnapshot dsx: snapshot.getChildren()){
                                                        CartItem cartItem1 = dsx.getValue(CartItem.class);
                                                        cartItem1.setNumberInCart(cartItem1.getNumberInCart() + cartItem.getNumberInCart());
                                                        FirebaseDatabase.getInstance().getReference("carts")
                                                                .child(ds.getKey()).child("cartItems")
                                                                .child(dsx.getKey()).setValue(cartItem1);
                                                        isUpdate = true;
                                                    }

                                                    if (!isUpdate) {
                                                        FirebaseDatabase.getInstance().getReference("carts")
                                                                .child(ds.getKey()).child("cartItems")
                                                                .push().setValue(cartItem);
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
                Intent intent = new Intent(ShowDetailActivity.this,CartListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.picfood);
    }
}