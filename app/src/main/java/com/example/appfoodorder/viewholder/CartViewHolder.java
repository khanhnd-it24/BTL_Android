package com.example.appfoodorder.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfoodorder.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView title, feeEachItem;
    ImageView pic, plusItem, minusItem;
    TextView totalEachItem, num;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleTxt);
        feeEachItem = itemView.findViewById(R.id.feeEachItem);
        pic = itemView.findViewById(R.id.picCart);
        totalEachItem = itemView.findViewById(R.id.totalEachItem);
        num = itemView.findViewById(R.id.numberItemTxt);
        plusItem = itemView.findViewById(R.id.plusCardBtn);
        minusItem = itemView.findViewById(R.id.minusCartBtn);
    }

    @Override
    public void onClick(View view) {

    }
}
