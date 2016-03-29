package com.automotive.hhi.mileagetracker.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Station;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class StationViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_station_name)
    TextView mName;
    @Bind(R.id.item_station_address)
    TextView mAddress;
    @Bind(R.id.item_station_city)
    TextView mCity;
    @Bind(R.id.item_station_state)
    TextView mState;
    @Bind(R.id.item_station_zip)
    TextView mZip;

    Station mHolderStation;

    public StationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setViewHolder(Station station){
        mHolderStation = station;
        mName.setText(station.getName());
        mAddress.setText(station.getAddress());
        mCity.setText(station.getCity());
        mState.setText(station.getState());
        mZip.setText(station.getZip());
    }
}
