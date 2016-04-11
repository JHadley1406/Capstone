package com.automotive.hhi.mileagetracker.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.adapters.CarAdapter;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailActivity;
import com.automotive.hhi.mileagetracker.view.CarListView;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarListPresenter implements Presenter<CarListView>, ViewHolderOnClickListener<Car>, LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = CarListPresenter.class.getSimpleName();


    private CarListView mCarListView;
    private Context mContext;
    private CarAdapter mCarListAdapter;


    @Override
    public void attachView(CarListView view) {
        mCarListView = view;
        mContext = view.getContext();
        mCarListAdapter = new CarAdapter(mContext, null, this);
        mCarListView.
    }

    @Override
    public void detachView() {
        mCarListView = null;
    }

    @Override
    public void onClick(Car car) {
        Intent carDetailIntent = new Intent(mContext, CarDetailActivity.class);
        carDetailIntent.putExtra(IntentContract.CAR_ID, car.getId());
        mCarListView.launchCarDetail(carDetailIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext
                , DataContract.CarTable.CONTENT_URI
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() == 0){
            mCarListView.addCar();
        } else if(data.getCount() == 1){
            data.moveToFirst();
            Car car = CarFactory.fromCursor(data);
            Intent carDetailIntent = new Intent(mContext, CarDetailActivity.class);
            carDetailIntent.putExtra(IntentContract.CAR_ID, car.getId());
            mCarListView.launchCarDetail(carDetailIntent);
        } else{
            mCarListAdapter.swapCursor(data);
            mCarListView.showCars(mCarListAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCarListView.getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
