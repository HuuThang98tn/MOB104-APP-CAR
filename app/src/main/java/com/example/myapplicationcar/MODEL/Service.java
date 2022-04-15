package com.example.myapplicationcar.MODEL;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable {
    private String id;
    private String name;
    private String type;
    private ArrayList<Integer> price;
    private String note;
    private String time;

    public Service(String id, String name, String type, ArrayList<Integer> price, String note, String time) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.note = note;
        this.time = time;
    }

    public Service() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Integer> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Integer> price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
