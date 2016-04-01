package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;

import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.SelectStationView;


/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class SelectStationPresenter implements Presenter<SelectStationView> {

    private SelectStationView mSelectStationView;
    private ContentResolver mContentResolver;

    @Override
    public void attachView(SelectStationView view) {
        mSelectStationView = view;
        mContentResolver = mSelectStationView.getContext().getContentResolver();
    }

    @Override
    public void detachView() {
        mSelectStationView = null;
        mContentResolver = null;
    }

    public void loadNearbyStations(){
       // mSelectStationView.showNearby();
    }

    public void loadUsedStations(){
        mSelectStationView.showUsed(mContentResolver
                .query(DataContract.StationTable.CONTENT_URI
                        , null, null, null, null));
    }
}
