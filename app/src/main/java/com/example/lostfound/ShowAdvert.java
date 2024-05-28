package com.example.lostfound;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowAdvert extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_advert);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<LostFoundItem> lostFoundItems = dbHelper.getAllLostFoundItems();

        if (lostFoundItems.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new MyAdapter(this, lostFoundItems, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                    // Handle item click if needed
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
