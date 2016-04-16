package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
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

    private final String LOG_TAG = AddFillupPresenter.class.getSimpleName();

    private AddFillupView mAddFillupView;
    private Context mContext;
    private Station mStation;
    private Car mCar;
    private Fillup mFillup;

    public AddFillupPresenter(Fillup fillup, Car car, Station station, Context context){
        mContext = context;
        mStation = station;
        mCar = car;
        mFillup = fillup;

    }

    @Override
    public void attachView(AddFillupView view) {
        mAddFillupView = view;
    }

    @Override
    public void detachView() {
        mAddFillupView = null;
        mContext = null;
    }

    public Car getCar(){ return mCar;}

    public Fillup getFillup() { return mFillup; }

    public Station getStation(){ return mStation; }

    public void checkStation(){
        if(mStation.getId()==0){
            Cursor fillupCheckCursor = mContext
                    .getContentResolver()
                    .query(DataContract.StationTable.CONTENT_URI
                            , null
                            , DataContract.StationTable.NAME
                            + " = '" + mStation.getName()
                            + "' AND " + DataContract.StationTable.ADDRESS
                            + " = '" + mStation.getAddress() + "'", null, null);

            if(fillupCheckCursor == null || !fillupCheckCursor.moveToFirst()){
                // If the station does not exist in the db, we add it, then add
                // the returned ID to the mStation object
                mStation.setId(ContentUris.parseId(mContext.getContentResolver()
                        .insert(DataContract.StationTable.CONTENT_URI
                                , StationFactory.toContentValues(mStation))));

            } else {
                // If the station does exist in the db, we just use the copy in the DB
                mStation = StationFactory.fromCursor(fillupCheckCursor);
            }
            if(fillupCheckCursor != null){
                fillupCheckCursor.close();
            }

        }
    }

    public void insertFillup(Fillup fillup){
        fillup.setStationId(mStation.getId());
        fillup.setCarId(mCar.getId());
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
        String sortOrder = "date DESC";
        // fillupCount is at least 1, since we're passing one into this method
        int fillupCount = 1;
        double mpgTotal = 0;
        Cursor allFillups = mContext.getContentResolver().query(DataContract.FillupTable.CONTENT_URI, null, DataContract.FillupTable.CAR + " = " + mCar.getId(), null, sortOrder);
        // fillup MPG is calculated by subtracting the previous fillup's mileage
        // from the current fillup's mileage, then dividing by
        // the gallons of fuel in this fillup
        if(allFillups != null && allFillups.moveToFirst()) {
            fillupCount += allFillups.getCount();
            Fillup prevFillup = FillupFactory.fromCursor(allFillups);
            fillup.setFillupMpg((fillup.getFillupMileage() - prevFillup.getFillupMileage()) / fillup.getGallons());
            mpgTotal = prevFillup.getFillupMpg() + fillup.getFillupMpg();

            while (allFillups.moveToNext()) {
                mpgTotal += allFillups.getInt(allFillups.getColumnIndexOrThrow(DataContract.FillupTable.MPG));
            }


            allFillups.close();
        } else {
            fillup.setFillupMpg(0.0);
            mpgTotal = fillup.getFillupMpg();
        }
        mCar.setAvgMpg(mpgTotal / fillupCount);
        mContext.getContentResolver().update(DataContract.CarTable.CONTENT_URI, CarFactory.toContentValues(mCar), DataContract.CarTable._ID + " = " + mCar.getId(), null);
    }
}
