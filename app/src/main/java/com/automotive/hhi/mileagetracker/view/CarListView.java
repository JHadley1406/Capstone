package com.automotive.hhi.mileagetracker.view;

import com.automotive.hhi.mileagetracker.model.data.Car;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface CarListView extends MvpView {

    void showCars(List<Car> cars);
}