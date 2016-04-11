package com.automotive.hhi.mileagetracker.view;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;

import com.automotive.hhi.mileagetracker.adapters.CarAdapter;
import com.automotive.hhi.mileagetracker.model.data.Car;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface CarListView extends MvpView {

    void showCars(CarAdapter cars);

    void addCar();

    LoaderManager getLoaderManager();

    void launchCarDetail(Intent carIntent);
}
