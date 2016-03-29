package com.automotive.hhi.mileagetracker.view.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_car_name)
    TextView mName;
    @Bind(R.id.item_car_make)
    TextView mMake;
    @Bind(R.id.item_car_model)
    TextView mModel;
    @Bind(R.id.item_car_year)
    TextView mYear;
    @Bind(R.id.item_car_mpg)
    TextView mMpg;

    Car mHolderCar;


    public CarViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setViewHolder(Car car){
        mHolderCar = car;
        mName.setText(car.getName());
        mMake.setText(car.getMake());
        mModel.setText(car.getModel());
        mYear.setText(car.getYear());
        mMpg.setText(Double.toString(car.getAvgMpg()));
    }
}
