package com.automotive.hhi.mileagetracker.model.data;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Fillup {

    private int id;
    private Car car;
    private Station station;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public void setDate(long date) {
        this.date = date;
    }
}
