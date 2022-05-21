package com.example.appfoodorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfoodorder.R;
import com.example.appfoodorder.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<CartItem> cartItems;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt;

    public CartListAdapter(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public CartListAdapter(ArrayList<CartItem> cartItems, TextView totalFeeTxt, TextView taxTxt, TextView deliveryTxt, TextView totalTxt) {
        this.cartItems = cartItems;
        this.totalFeeTxt = totalFeeTxt;
        this.taxTxt = taxTxt;
        this.deliveryTxt = deliveryTxt;
        this.totalTxt = totalTxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(cartItems.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(cartItems.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((cartItems.get(position).getNumberInCart() * cartItems.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(cartItems.get(position).getNumberInCart()));

        int drawableReourceId = holder.itemView.getContext().getResources().getIdentifier(cartItems.get(position).getPic()
                , "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                FirebaseDatabase.getInstance().getReference("carts")
                        .orderByChild("userUid").equalTo(user.getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    FirebaseDatabase.getInstance().getReference("carts")
                                            .child(ds.getKey()).child("cartItems")
                                            .orderByChild("title").equalTo(cartItems.get(position).getTitle())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                                                    for(DataSnapshot dsx: snapshot.getChildren()){
                                                        CartItem cartItem1 = dsx.getValue(CartItem.class);
                                                        cartItem1.setNumberInCart(cartItem1.getNumberInCart() + 1);
                                                        FirebaseDatabase.getInstance().getReference("carts")
                                                                .child(ds.getKey()).child("cartItems")
                                                                .child(dsx.getKey()).setValue(cartItem1);
                                                        holder.num.setText(String.valueOf(cartItem1.getNumberInCart()));
                                                        holder.totalEachItem.setText(String.valueOf(Math.round((cartItem1.getNumberInCart() * cartItem1.getFee()) * 100) / 100));
                                                        cartItems.set(position,cartItem1);
                                                    }
                                                    CalculateCart(cartItems);
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
                notifyDataSetChanged();
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                FirebaseDatabase.getInstance().getReference("carts")
                        .orderByChild("userUid").equalTo(user.getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    FirebaseDatabase.getInstance().getReference("carts")
                                            .child(ds.getKey()).child("cartItems")
                                            .orderByChild("title").equalTo(cartItems.get(position).getTitle())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                                                    for(DataSnapshot dsx: snapshot.getChildren()){
                                                        CartItem cartItem1 = dsx.getValue(CartItem.class);
                                                        cartItem1.setNumberInCart(cartItem1.getNumberInCart() - 1);
                                                        FirebaseDatabase.getInstance().getReference("carts")
                                                                .child(ds.getKey()).child("cartItems")
                                                                .child(dsx.getKey()).setValue(cartItem1);
                                                        holder.num.setText(String.valueOf(cartItem1.getNumberInCart()));
                                                        holder.totalEachItem.setText(String.valueOf(Math.round((cartItem1.getNumberInCart() * cartItem1.getFee()) * 100) / 100));
                                                        cartItems.set(position,cartItem1);
                                                    }
                                                    CalculateCart(cartItems);
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
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }

    private void CalculateCart(ArrayList<CartItem> cartItems) {
        double percentTax = 0.02;
        double delivery = 10;
        double totalPrice = 0;
        for(CartItem item: cartItems) {
            totalPrice += item.getFee() * item.getNumberInCart();
        }
        double tax = Math.round((totalPrice * percentTax) * 100) / 100;
        double total = Math.round((totalPrice + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(totalPrice * 100) / 100;

        totalFeeTxt.setText("$" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delivery);
        totalTxt.setText("$" + total);
    }
}


