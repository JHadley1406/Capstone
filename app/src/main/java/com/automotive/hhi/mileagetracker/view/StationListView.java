package com.automotive.hhi.mileagetracker.view;

import android.database.Cursor;

/**
 * Created by Josiah Hadley on 3/31/2016.
 */
public interface StationListView extends MvpView {

    void showStations(Cursor stations);

}
