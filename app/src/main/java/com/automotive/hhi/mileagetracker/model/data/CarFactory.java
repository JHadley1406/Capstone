package com.automotive.hhi.mileagetracker.model.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarFactory extends Car {

    public Car fromCursor(Cursor cursor){
        Car car = new Car();

        return car;
    }

    public ContentValues toContentValues(Car car){
        ContentValues vals = new ContentValues();


        return vals;
    }
}
