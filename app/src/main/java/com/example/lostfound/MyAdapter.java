package com.example.lostfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<LostFoundItem> lostFoundItems;
    private AdapterView.OnItemClickListener listener;

    public MyAdapter(Context context, List<LostFoundItem> lostFoundItems, AdapterView.OnItemClickListener listener) {
        this.context = context;
        this.lostFoundItems = lostFoundItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.advert_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LostFoundItem currentItem = lostFoundItems.get(position);
        holder.layout_type.setText(currentItem.getType());
        holder.layout_desc.setText(currentItem.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedAdvert.class);
                intent.putExtra("Name", currentItem.getName());
                intent.putExtra("Date", currentItem.getDate());
                intent.putExtra("Desc", currentItem.getDesc());
                intent.putExtra("Phone", currentItem.getPhone());
                intent.putExtra("Location", currentItem.getLocation());
                intent.putExtra("Type", currentItem.getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lostFoundItems.size();
    }
}
