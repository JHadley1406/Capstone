package com.automotive.hhi.mileagetracker.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.view.ViewGroup;

import com.automotive.hhi.mileagetracker.view.viewholders.StationViewHolder;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class StationAdapter extends CursorRecyclerViewAdapter<StationViewHolder> {

    public StationAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }
    @Override
    public void onBindViewHolder(StationViewHolder viewHolder, Cursor cursor) {

    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
