package com.example.lostfound;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewAdvert extends AppCompatActivity {
    TextInputEditText name, phone, description, date, locationEditText;
    Button submit, getCurrentLocation;
    DatabaseHelper dbHelper;
    String type = "";
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String> locationPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        dbHelper = new DatabaseHelper(this);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        locationEditText = findViewById(R.id.editTextLocation);
        submit = findViewById(R.id.submit);
        getCurrentLocation = findViewById(R.id.buttonGetCurrentLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permission
        locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                getCurrentLocation();
            } else {
                Toast.makeText(NewAdvert.this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        });

        // On submit button click
        submit.setOnClickListener(view -> {
            String nameText = name.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();
            String descText = description.getText().toString().trim();
            String dateText = date.getText().toString().trim();
            String locationText = locationEditText.getText().toString().trim();

            if (nameText.isEmpty() || phoneText.isEmpty() || descText.isEmpty() || dateText.isEmpty() || locationText.isEmpty()) {
                Toast.makeText(NewAdvert.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save data to the database
            dbHelper.insertLostFoundItem(nameText, descText, dateText, locationText, phoneText, type);
        });

        // On getCurrentLocation button click
        getCurrentLocation.setOnClickListener(view -> checkLocationPermission());
    }

    // Method to check location permission
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(NewAdvert.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    // Method to get current location
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(NewAdvert.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            String address = getAddressFromLocation(latitude, longitude);
                            if (address != null) {
                                Log.d("Address", "Address: " + address);
                                locationEditText.setText(address);
                            } else {
                                Log.e("Address", "Failed to retrieve address from location");
                            }
                        } else {
                            Log.e("Location", "Last known location is null");
                            Toast.makeText(NewAdvert.this, "Failed to get current location. Please make sure your GPS is enabled.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(this, e -> {
                        Log.e("Location", "Failed to get current location", e);
                        Toast.makeText(NewAdvert.this, "Failed to get current location. Please try again later.", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Method to get address from latitude and longitude
    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(NewAdvert.this, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                result = sb.toString();

                Log.d("Address", "Address: " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
