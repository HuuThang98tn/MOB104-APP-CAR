package com.example.myapplicationcar.MODEL;

import java.io.Serializable;

public class Oder implements Serializable {
    private String user_id;
    private String service_id;
    private String number_car;
    private String type_car;
    private String name_car;
    private String date;
    private String time;

    public Oder() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getNumber_car() {
        return number_car;
    }

    public void setNumber_car(String number_car) {
        this.number_car = number_car;
    }

    public String getType_car() {
        return type_car;
    }

    public void setType_car(String type_car) {
        this.type_car = type_car;
    }

    public String getName_car() {
        return name_car;
    }

    public void setName_car(String name_car) {
        this.name_car = name_car;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Oder{" +
                "user_id='" + user_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", number_car='" + number_car + '\'' +
                ", type_car='" + type_car + '\'' +
                ", name_car='" + name_car + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
