package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.FillupFactory;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.data.StationFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.AddFillupView;

/**
 * Created by Josiah Hadley on 4/1/2016.
 */
public class AddFillupPresenter implements Presenter<AddFillupView> {

    private AddFillupView mAddFillupView;
    private Context mContext;
    private Station mStation;
    private long mCarId;

    public AddFillupPresenter(long carId, Station station){
        mCarId = carId;
        mStation = station;
    }

    @Override
    public void attachView(AddFillupView view) {
        mAddFillupView = view;
        mContext = view.getContext();
    }

    @Override
    public void detachView() {
        mAddFillupView = null;
        mContext = null;
    }

    public void checkStation(){
        if(mStation.getId() == 0){
            Uri newStationUri = mContext.getContentResolver()
                    .insert(DataContract.StationTable.CONTENT_URI
                            , StationFactory.toContentValues(mStation));
            mStation.setId(ContentUris.parseId(newStationUri));
        }
    }

    public void insertFillup(Fillup fillup){
        fillup.setStationId(mStation.getId());
        fillup.setCarId(mCarId);
        calculateMpg(fillup);
        mContext.getContentResolver().insert(DataContract.FillupTable.CONTENT_URI
                , FillupFactory.toContentValues(fillup));
    }

    public boolean validateInput(LinearLayout container){
        for(int i=0; i < container.getChildCount(); i++){
            View v = container.getChildAt(i);
            if(v instanceof EditText){
                if(TextUtils.isEmpty(((EditText) v).getText().toString())){
                    ((EditText) v).setHintTextColor(Color.RED);
                    ((EditText) v).setError(mContext.getResources()
                            .getString(R.string.edit_text_error));
                    return false;
                }
            }
        }
        return true;
    }

    private void calculateMpg(Fillup fillup){
        fillup.setCarId(mCarId);
        String sortOrder = "date DESC";
        int fillupCount = 1;
        double mileageTotal = 0;
        Cursor allFillups = mContext.getContentResolver().query(DataContract.FillupTable.CONTENT_URI, null, DataContract.FillupTable.CAR + " = " + mCarId, null, sortOrder);
        // fillup MPG is calculated by subtracting the previous fillup's mileage
        // from the current fillup's mileage, then dividing by
        // the gallons of fuel in this fillup
        if(allFillups != null) {
            if (allFillups.moveToFirst()) {
                fillupCount = allFillups.getCount();
                Fillup prevFillup = FillupFactory.fromCursor(allFillups);
                fillup.setFillupMpg((fillup.getFillupMileage() - prevFillup.getFillupMileage()) / fillup.getGallons());
            }

            while (allFillups.moveToNext()) {
                mileageTotal += allFillups.getInt(allFillups.getColumnIndexOrThrow(DataContract.FillupTable.MPG));
            }
            mileageTotal += fillup.getFillupMpg();
            allFillups.close();
        }
        Cursor carCursor = mContext.getContentResolver().query(DataContract.CarTable.CONTENT_URI, null, DataContract.CarTable._ID + " = " + mCarId, null, null);
        if(carCursor != null && carCursor.moveToFirst()){
            Car car = CarFactory.fromCursor(carCursor);
            car.setAvgMpg(mileageTotal/fillupCount);
            mContext.getContentResolver().insert(DataContract.CarTable.CONTENT_URI, CarFactory.toContentValues(car));
            mContext.getContentResolver().insert(DataContract.FillupTable.CONTENT_URI, FillupFactory.toContentValues(fillup));
            carCursor.close();
        }
    }
}
