package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;

import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailView;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarDetailPresenter implements Presenter<CarDetailView> {

    private CarDetailView mCarDetailView;
    private ContentResolver mContentResolver;
    public int mCurrentCarId;

    @Override
    public void attachView(CarDetailView view) {
        mCarDetailView = view;
        mContentResolver = mCarDetailView.getContext().getContentResolver();
    }

    @Override
    public void detachView() {
        mCarDetailView = null;
        mContentResolver = null;
    }

    public void loadFillups(){
        String sortOrder = "date DESC";
        mCarDetailView.showFillups(mContentResolver
                .query(DataContract.FillupTable.CONTENT_URI
                        , null, "car = " + mCurrentCarId, null, sortOrder));
    }

    public void loadCar(){
        mCarDetailView.showCar(CarFactory
                .fromCursor(mContentResolver
                        .query(DataContract.CarTable.CONTENT_URI
                                , null, "_id = " + mCurrentCarId
                                , null, null)));

    }

    public void setCurrentCarid(int carId){
        mCurrentCarId = carId;
    }


}
