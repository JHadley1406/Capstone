package com.automotive.hhi.mileagetracker.presenter;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.adapters.FillupAdapter;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.FillupFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailView;
import com.automotive.hhi.mileagetracker.view.SelectStationActivity;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarDetailPresenter implements Presenter<CarDetailView>, LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = CarDetailPresenter.class.getSimpleName();
    private final int DETAIL_FILLUPS_LOADER_ID = 543219876;

    private CarDetailView mCarDetailView;
    private Context mContext;
    private FillupAdapter mFillupAdapter;
    private LoaderManager mLoaderManager;

    public long mCurrentCarId;

    public CarDetailPresenter(Context context, LoaderManager loaderManager, long carId){
        mContext = context;
        mLoaderManager = loaderManager;
        mFillupAdapter = new FillupAdapter(mContext, null);
        mCurrentCarId = carId;
    }


    @Override
    public void attachView(CarDetailView view) {
        mCarDetailView = view;
        mLoaderManager.initLoader(DETAIL_FILLUPS_LOADER_ID, null, this);
    }

    @Override
    public void detachView() {
        mCarDetailView = null;
    }

    public void loadCar(){
        Cursor carCursor = mContext.getContentResolver()
                .query(DataContract.CarTable.CONTENT_URI
                        , null, DataContract.CarTable._ID + " = " + mCurrentCarId
                        , null, null);
        if(carCursor != null && carCursor.moveToFirst()) {
            mCarDetailView.showCar(CarFactory
                    .fromCursor(carCursor));
            carCursor.close();
        }

    }

    public void launchSelectStation() {
        Intent selectStationIntent = new Intent(mContext, SelectStationActivity.class);
        selectStationIntent.putExtra(IntentContract.CAR_ID, mCurrentCarId);
        mCarDetailView.launchSelectStation(selectStationIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = "date DESC";
        Log.i(LOG_TAG, "Current Car ID : " + mCurrentCarId);
        return new CursorLoader(mContext
                , DataContract.FillupTable.CONTENT_URI
                , null
                , DataContract.FillupTable.CAR + " = " + mCurrentCarId
                , null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFillupAdapter.changeCursor(data);
        mCarDetailView.showFillups(mFillupAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoaderManager.restartLoader(DETAIL_FILLUPS_LOADER_ID, null, this);
    }
}
