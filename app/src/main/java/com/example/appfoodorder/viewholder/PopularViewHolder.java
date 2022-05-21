package com.example.appfoodorder.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodorder.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView title, fee;
    public ImageView pic;
    public TextView addBtn;

    public PopularViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        fee = itemView.findViewById(R.id.fee);
        pic = itemView.findViewById(R.id.pic);
        addBtn = itemView.findViewById(R.id.addBtn);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}