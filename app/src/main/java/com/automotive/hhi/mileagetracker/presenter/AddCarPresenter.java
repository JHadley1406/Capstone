package com.automotive.hhi.mileagetracker.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.CarFactory;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.AddCarView;

/**
 * Created by Josiah Hadley on 4/1/2016.
 */
public class AddCarPresenter implements Presenter<AddCarView> {

    private AddCarView mAddCarView;
    private Context mContext;

    @Override
    public void attachView(AddCarView view) {
        mAddCarView = view;
        mContext = view.getContext();
    }

    @Override
    public void detachView() {
        mAddCarView = null;
        mContext = null;
    }

    public void insertCar(Car car){
        mContext.getContentResolver().insert(DataContract.CarTable.CONTENT_URI
                , CarFactory.toContentValues(car));
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

}
