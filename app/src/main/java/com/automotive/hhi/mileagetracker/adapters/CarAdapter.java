package com.automotive.hhi.mileagetracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.ViewGroup;

import com.automotive.hhi.mileagetracker.view.viewholders.CarViewHolder;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class CarAdapter extends CursorRecyclerViewAdapter<CarViewHolder> {


    public CarAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(CarViewHolder viewHolder, Cursor cursor) {

    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
