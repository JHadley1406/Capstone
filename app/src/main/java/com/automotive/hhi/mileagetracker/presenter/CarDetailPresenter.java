package com.automotive.hhi.mileagetracker.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.automotive.hhi.mileagetracker.KeyContract;
import com.automotive.hhi.mileagetracker.adapters.FillupAdapter;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.data.StationFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailView;
import com.automotive.hhi.mileagetracker.view.SelectStationActivity;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarDetailPresenter implements Presenter<CarDetailView>
        , ViewHolderOnClickListener<Fillup>
        , LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = CarDetailPresenter.class.getSimpleName();
    private final int DETAIL_FILLUPS_LOADER_ID = 543219876;

    private CarDetailView mCarDetailView;
    private Context mContext;
    private FillupAdapter mFillupAdapter;
    private LoaderManager mLoaderManager;

    public Car mCurrentCar;

    public CarDetailPresenter(Context context, LoaderManager loaderManager, Car car){
        mContext = context;
        mLoaderManager = loaderManager;
        mFillupAdapter = new FillupAdapter(mContext, null, this);
        mCurrentCar = car;
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
        mCarDetailView.showCar(mCurrentCar);
    }

    public void updateCar(Car car){
        mCurrentCar = car;
        loadCar();
    }

    public void deleteCar(){
        mContext.getContentResolver()
                .delete(DataContract.CarTable.CONTENT_URI
                        , DataContract.CarTable._ID + " = " + mCurrentCar.getId()
                        , null);
        mContext.getContentResolver()
                .delete(DataContract.FillupTable.CONTENT_URI
                        , DataContract.FillupTable.CAR + " = " + mCurrentCar.getId()
                        , null);
        mCarDetailView.close();
    }

    public void launchSelectStation() {
        Intent selectStationIntent = new Intent(mContext, SelectStationActivity.class);
        selectStationIntent.putExtra(KeyContract.CAR, mCurrentCar);
        mCarDetailView.launchSelectStation(selectStationIntent);
    }


    @Override
    public void onClick(Fillup fillup){
        Cursor stationCursor = mContext.getContentResolver().query(
                DataContract.StationTable.CONTENT_URI
                , null
                , DataContract.StationTable._ID + " = " + fillup.getStationId()
                , null, null);
        if(stationCursor.moveToFirst()) {
            Station station = StationFactory.fromCursor(stationCursor);
            mCarDetailView.launchEditFillup(mCurrentCar, station, fillup);
        }
    }


    public void launchEditCar(){
        mCarDetailView.launchEditCar(mCurrentCar);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = "date DESC";
        Log.i(LOG_TAG, "Current Car ID : " + mCurrentCar.getId());
        return new CursorLoader(mContext
                , DataContract.FillupTable.CONTENT_URI
                , null
                , DataContract.FillupTable.CAR + " = " + mCurrentCar.getId()
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
