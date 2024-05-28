package com.example.lostfound;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView layout_type,layout_desc;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        layout_desc = itemView.findViewById(R.id.layout_desc);
        layout_type = itemView.findViewById(R.id.layout_type);
    }
}
