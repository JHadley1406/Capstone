package com.automotive.hhi.mileagetracker.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Station {

    private long id;
    private String name;
    private String address;
    private double lat;
    private double lon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
