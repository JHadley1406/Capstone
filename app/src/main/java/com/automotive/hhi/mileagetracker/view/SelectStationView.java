package com.automotive.hhi.mileagetracker.view;

import android.database.Cursor;

import com.automotive.hhi.mileagetracker.adapters.LocBasedStationAdapter;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface SelectStationView extends MvpView {

    void showNearby(LocBasedStationAdapter stations);

    void showUsed(StationAdapter stations);

    void addFillup(long carId, Station station);

}
