package com.automotive.hhi.mileagetracker.model.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Fillup {

    private int id;
    private int carId;
    private int stationId;
    private double fillupMileage;
    private double fillupMpg;
    private double gallons;
    private double fuelCost;
    private int octane;
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int car) {
        this.carId = car;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int station) {
        this.stationId = station;
    }

    public double getFillupMileage() {
        return fillupMileage;
    }

    public void setFillupMileage(double fillupMileage) {
        this.fillupMileage = fillupMileage;
    }

    public double getFillupMpg() {
        return fillupMpg;
    }

    public void setFillupMpg(double fillupMpg) {
        this.fillupMpg = fillupMpg;
    }

    public double getGallons() {
        return gallons;
    }

    public void setGallons(double gallons) {
        this.gallons = gallons;
    }

    public double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public int getOctane() {
        return octane;
    }

    public void setOctane(int octane) {
        this.octane = octane;
    }

    public long getDate() {
        return date;
    }

    public String getReadableDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(new Date(getDate()));
    }

    public void setDate(long date) {
        this.date = date;
    }
}
