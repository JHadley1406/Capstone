package com.automotive.hhi.mileagetracker.model.data;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class Car {

    private long id;
    private String name;
    private String make;
    private String model;
    private int year;
    private double startingMileage;
    private double avgMpg;

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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getStartingMileage() {
        return startingMileage;
    }

    public void setStartingMileage(double startingMileage) {
        this.startingMileage = startingMileage;
    }

    public double getAvgMpg() {
        return avgMpg;
    }

    public void setAvgMpg(double avgMpg) {
        this.avgMpg = avgMpg;
    }




}
