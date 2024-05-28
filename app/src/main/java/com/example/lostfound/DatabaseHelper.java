package com.example.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database information
    private static final String DATABASE_NAME = "lost_found.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Implement onCreate method to create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the lost_found table
        String SQL_CREATE_LOST_FOUND_TABLE = "CREATE TABLE " + LostFoundContract.TABLE_NAME + " ("
                + LostFoundContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LostFoundContract.COLUMN_NAME + " TEXT NOT NULL, "
                + LostFoundContract.COLUMN_DESCRIPTION + " TEXT, "
                + LostFoundContract.COLUMN_DATE + " TEXT, "
                + LostFoundContract.COLUMN_LOCATION + " TEXT, "
                + LostFoundContract.COLUMN_PHONE + " TEXT, "
                + LostFoundContract.COLUMN_TYPE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_LOST_FOUND_TABLE);
    }

    // Implement onUpgrade method to handle database upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
    }

    // Method to insert a new lost and found item
    public long insertLostFoundItem(String nameText, String descText, String dateText, String locationText, String phoneText, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LostFoundContract.COLUMN_NAME, nameText);
        values.put(LostFoundContract.COLUMN_DESCRIPTION, descText);
        values.put(LostFoundContract.COLUMN_DATE, dateText);
        values.put(LostFoundContract.COLUMN_LOCATION, locationText);
        values.put(LostFoundContract.COLUMN_PHONE, phoneText);
        values.put(LostFoundContract.COLUMN_TYPE, type);

        long newRowId = db.insert(LostFoundContract.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    // Method to delete a lost and found item
    public boolean deleteAdvert(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = LostFoundContract.COLUMN_PHONE + " = ?";
        String[] selectionArgs = { phone };
        int deletedRows = db.delete(LostFoundContract.TABLE_NAME, selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }

    // Method to retrieve all lost and found items from the database
    public List<LostFoundItem> getAllLostFoundItems() {
        List<LostFoundItem> lostFoundItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                LostFoundContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_TYPE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_DESCRIPTION));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_LOCATION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_DATE));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_PHONE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(LostFoundContract.COLUMN_NAME));
                lostFoundItems.add(new LostFoundItem(type, desc, location, date, phone, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lostFoundItems;
    }
}
