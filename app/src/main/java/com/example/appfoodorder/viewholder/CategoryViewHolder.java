package com.example.appfoodorder.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodorder.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView categoryName;
    public ImageView categoryPic;
    public ConstraintLayout mainLayout;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.categoryName);
        categoryPic = itemView.findViewById(R.id.categoryPic);
        mainLayout = itemView.findViewById(R.id.mainLayout);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
