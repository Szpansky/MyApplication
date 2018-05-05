package com.example.empty.myapplication;

import java.io.Serializable;

public class Route implements Serializable {

    int id;
    String name;
    int duration;
    double distance;
    String thumb_id;
    double latitude;
    double longitude;


    public Route(int id, String name, int duration, double distance, String thumb_id, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.distance = distance;
        this.thumb_id = thumb_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public String getThumb_id() {
        return thumb_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
