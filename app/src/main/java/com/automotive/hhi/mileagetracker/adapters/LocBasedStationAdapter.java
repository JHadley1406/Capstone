package com.automotive.hhi.mileagetracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.StationOnClickListener;
import com.automotive.hhi.mileagetracker.view.viewholders.StationViewHolder;

import java.util.List;

/**
 * Created by Josiah Hadley on 4/5/2016.
 */
public class LocBasedStationAdapter extends RecyclerView.Adapter<StationViewHolder> {

    private List<Station> mStations;
    private StationOnClickListener mOnClickListener;

    public LocBasedStationAdapter(List<Station> stations, StationOnClickListener onClickListener){
        mStations = stations;
        mOnClickListener = onClickListener;
    }


    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.station_item, parent, false);

        return new StationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, int position) {
        holder.setViewHolder(mStations.get(position), mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}