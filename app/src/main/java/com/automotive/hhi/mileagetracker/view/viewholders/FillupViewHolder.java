package com.automotive.hhi.mileagetracker.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Fillup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class FillupViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_fillup_station_name)
    TextView mStationName;
    @Bind(R.id.item_fillup_gallons)
    TextView mGallons;
    @Bind(R.id.item_fillup_cost)
    TextView mFuelCost;
    @Bind(R.id.item_fillup_octane)
    TextView mOctane;
    @Bind(R.id.item_fillup_mpg)
    TextView mFillupMpg;
    @Bind(R.id.item_fillup_date)
    TextView mDate;

    Fillup mHolderFillup;

    public FillupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setViewHolder(Fillup fillup){
        mHolderFillup = fillup;
        mStationName.setText(fillup.getStation().getName());
        mGallons.setText(Double.toString(fillup.getGallons()));
        mFuelCost.setText(Double.toString(fillup.getFuelCost()));
        mOctane.setText(Integer.toString(fillup.getOctane()));
        mFillupMpg.setText(Double.toString(fillup.getFillupMpg()));
        mDate.setText(fillup.getReadableDate());
    }
}
