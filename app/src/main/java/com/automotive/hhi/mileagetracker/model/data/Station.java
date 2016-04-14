package com.automotive.hhi.mileagetracker.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Station implements Parcelable{

    private long id;
    private String name;
    private String address;
    private double lat;
    private double lon;

    public Station(){}

    public Station(Parcel in){
        setId(in.readInt());
        setName(in.readString());
        setAddress(in.readString());
        setLat(in.readDouble());
        setLon(in.readDouble());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getAddress());
        dest.writeDouble(getLat());
        dest.writeDouble(getLon());
    }

    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>(){
        public Station createFromParcel(Parcel in){ return new Station(in);}

        public Station[] newArray(int size){ return new Station[size]; }
    };
}
