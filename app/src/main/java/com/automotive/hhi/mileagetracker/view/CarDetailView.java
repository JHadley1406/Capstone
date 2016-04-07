package com.automotive.hhi.mileagetracker.view;

import android.content.Intent;
import android.database.Cursor;

import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface CarDetailView extends MvpView {

    void showFillups(Cursor fillups);

    void showCar(Car car);

    void launchSelectStation(Intent selectStationIntent);
    
}
