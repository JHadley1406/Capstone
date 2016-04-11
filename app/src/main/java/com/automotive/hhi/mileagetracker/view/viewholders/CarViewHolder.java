package com.automotive.hhi.mileagetracker.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.presenter.ViewHolderOnClickListener;

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

    public void setViewHolder(Car car, final ViewHolderOnClickListener<Car> selectedCarListener){
        mHolderCar = car;
        mName.setText(car.getName());
        mMake.setText(car.getMake());
        mModel.setText(car.getModel());
        mYear.setText(String.format("%d", car.getYear()));
        mMpg.setText(String.format("%.1f", car.getAvgMpg()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCarListener.onClick(mHolderCar);
            }
        });
    }
}
