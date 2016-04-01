package com.automotive.hhi.mileagetracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.StationFactory;
import com.automotive.hhi.mileagetracker.view.viewholders.StationViewHolder;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class StationAdapter extends CursorRecyclerViewAdapter<StationViewHolder> {

    private Context mContext;

    public StationAdapter(Context context, Cursor cursor){
        super(context, cursor);
        mContext = context;
    }
    @Override
    public void onBindViewHolder(StationViewHolder viewHolder, Cursor cursor) {
        viewHolder.setViewHolder(StationFactory.fromCursor(cursor));
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.station_item, parent, false);

        return new StationViewHolder(itemView);

    }
}
