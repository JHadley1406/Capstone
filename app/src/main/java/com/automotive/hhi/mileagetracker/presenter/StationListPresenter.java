package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;

import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.StationListView;

/**
 * Created by Josiah Hadley on 3/31/2016.
 */
public class StationListPresenter implements Presenter<StationListView> {

    private StationListView mStationListView;
    private ContentResolver mContentResolver;

    @Override
    public void attachView(StationListView view) {
        mStationListView = view;
        mContentResolver = mStationListView.getContext().getContentResolver();
    }

    @Override
    public void detachView() {
        mStationListView = null;
        mContentResolver = null;
    }

    public void loadStations(){
        mStationListView.showStations(mContentResolver
                .query(DataContract.StationTable.CONTENT_URI
                        , null, null, null, null));
    }
}
