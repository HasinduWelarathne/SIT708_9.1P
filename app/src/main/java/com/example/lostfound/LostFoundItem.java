package com.example.lostfound;

public class LostFoundItem {
    private String type;
    private String desc;
    private String location;
    private String date;
    private String phone;
    private String name;

    public LostFoundItem(String type, String desc, String location, String date, String phone, String name) {
        this.type = type;
        this.desc = desc;
        this.location = location;
        this.date = date;
        this.phone = phone;
        this.name = name;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}

