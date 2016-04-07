package com.automotive.hhi.mileagetracker.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarDetailActivity;
import com.automotive.hhi.mileagetracker.view.CarListView;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarListPresenter implements Presenter<CarListView> {

    private CarListView mCarListView;
    private Context mContext;


    @Override
    public void attachView(CarListView view) {
        mCarListView = view;
        mContext = view.getContext();
    }

    @Override
    public void detachView() {
        mCarListView = null;
    }

    public void loadCars(){
        Cursor carCursor =mCarListView
                .getContext()
                .getContentResolver()
                .query(DataContract.CarTable.CONTENT_URI
                        , null, null, null, null);
        if(carCursor == null || carCursor.getCount() == 0){
            mCarListView.addCar();
        } else if(carCursor.getCount() == 1){
            carCursor.moveToFirst();
            Car car = CarFactory.fromCursor(carCursor);
            Intent carDetailIntent = new Intent(mContext, CarDetailActivity.class);
            carDetailIntent.putExtra(IntentContract.CAR_ID, car.getId());
            mCarListView.getContext().startActivity(new Intent());
        } else{
            mCarListView.showCars(carCursor);
        }
    }


}
