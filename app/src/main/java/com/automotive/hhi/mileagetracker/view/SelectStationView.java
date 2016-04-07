package com.automotive.hhi.mileagetracker.view;

import android.database.Cursor;

import com.automotive.hhi.mileagetracker.model.data.Station;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface SelectStationView extends MvpView {

    void showNearby(List<Station> stations);

    void showUsed(Cursor stations);

    void addFillup(int carId, Station station);

}
