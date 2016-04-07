package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailView;
import com.automotive.hhi.mileagetracker.view.SelectStationActivity;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarDetailPresenter implements Presenter<CarDetailView> {

    private CarDetailView mCarDetailView;
    private Context mContext;
    public int mCurrentCarId;

    @Override
    public void attachView(CarDetailView view) {
        mCarDetailView = view;
        mContext = mCarDetailView.getContext();
    }

    @Override
    public void detachView() {
        mCarDetailView = null;
        mContentResolver = null;
    }

    public void loadFillups(){
        String sortOrder = "date DESC";
        mCarDetailView.showFillups(mContext.getContentResolver()
                .query(DataContract.FillupTable.CONTENT_URI
                        , null, "car = " + mCurrentCarId, null, sortOrder));
    }

    public void loadCar(){
        mCarDetailView.showCar(CarFactory
                .fromCursor(mContext.getContentResolver()
                        .query(DataContract.CarTable.CONTENT_URI
                                , null, "_id = " + mCurrentCarId
                                , null, null)));

    }

    public void setCurrentCarid(int carId){
        mCurrentCarId = carId;
    }

    public void launchSelectStation(){}
    Intent selectStationIntent = new Intent(mContext, SelectStationActivity.class);
    selectStationIntent.  (IntentContract.CAR_ID, mCurrentCarId);

}
