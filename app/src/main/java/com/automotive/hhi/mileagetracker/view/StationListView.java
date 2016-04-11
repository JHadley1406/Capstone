package com.automotive.hhi.mileagetracker.view;

import android.database.Cursor;

import com.automotive.hhi.mileagetracker.adapters.StationAdapter;

/**
 * Created by Josiah Hadley on 3/31/2016.
 */
public interface StationListView extends MvpView {

    void showStations(StationAdapter stations);

}
