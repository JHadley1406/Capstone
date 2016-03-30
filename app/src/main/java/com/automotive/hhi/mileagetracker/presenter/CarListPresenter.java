package com.automotive.hhi.mileagetracker.presenter;

import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.CarListView;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarListPresenter implements Presenter<CarListView> {

    private CarListView mCarListView;


    @Override
    public void attachView(CarListView view) {
        mCarListView = view;
    }

    @Override
    public void detachView() {
        mCarListView = null;
    }

    public void loadCars(){
        mCarListView.showCars(mCarListView
                .getContext()
                .getContentResolver()
                .query(DataContract.CarTable.CONTENT_URI
                        , null, null, null, null));
    }

}
