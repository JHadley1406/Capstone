package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;
import android.util.Log;

import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.StationListView;

/**
 * Created by Josiah Hadley on 3/31/2016.
 */
public class StationListPresenter implements Presenter<StationListView>
        , StationOnClickListener {

    private final String LOG_TAG = StationListPresenter.class.getSimpleName();

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

    @Override
    public void onClick(Station station) {
        Log.i(LOG_TAG, "Station Clicked On");
    }
}
