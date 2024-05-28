package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfound.DatabaseHelper;
import com.example.lostfound.R;

public class DetailedAdvert extends AppCompatActivity {

    TextView name, date, phone, location, desc, type;
    Button delete;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_advert);
        name = findViewById(R.id.textViewName);
        date = findViewById(R.id.textViewDate);
        desc = findViewById(R.id.textViewDescription);
        phone = findViewById(R.id.textViewPhone);
        location = findViewById(R.id.textViewLocation);
        delete = findViewById(R.id.buttonDelete);
        type = findViewById(R.id.textViewTitleLabel);

        dbHelper = new DatabaseHelper(this);

        String type_i = getIntent().getStringExtra("Type");
        String description_i = getIntent().getStringExtra("Desc");
        String name_i = getIntent().getStringExtra("Name");
        String phone_i = getIntent().getStringExtra("Phone");
        String date_i = getIntent().getStringExtra("Date");
        String location_i = getIntent().getStringExtra("Location");

        name.setText(name_i);
        date.setText(date_i);
        desc.setText(description_i);
        location.setText(location_i);
        phone.setText(phone_i);
        type.setText(type_i);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(phone_i);
            }
        });

    }

    private void deleteData(String phone) {
        boolean deleted = dbHelper.deleteAdvert(phone);
        if (deleted) {
            Toast.makeText(DetailedAdvert.this, "Advert successfully deleted!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(DetailedAdvert.this, "Failed to delete advert", Toast.LENGTH_SHORT).show();
        }
    }
}
