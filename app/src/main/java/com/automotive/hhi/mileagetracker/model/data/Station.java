package com.automotive.hhi.mileagetracker.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Station implements Parcelable {

    private int id;
    private String name;
    private String address;
    private double lat;
    private double lon;

    public Station(){}

    public Station(Parcel in){
        this.setId(in.readInt());
        this.setName(in.readString());
        this.setAddress(in.readString());
        this.setLat(in.readDouble());
        this.setLon(in.readDouble());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
        dest.writeString(this.getAddress());
        dest.writeDouble(this.getLat());
        dest.writeDouble(this.getLon());
    }
}
