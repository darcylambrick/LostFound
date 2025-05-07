package com.example.lostfound;

import java.io.Serializable;

public class Item implements Serializable {
    public int id;
    public String title, description, type, phone, date, location;

    public Item(int id, String title, String description, String type,
                String phone, String date, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.phone = phone;
        this.date = date;
        this.location = location;
    }
}
