package com.automotive.hhi.mileagetracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.ViewGroup;

import com.automotive.hhi.mileagetracker.view.viewholders.FillupViewHolder;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class FillupAdapter extends CursorRecyclerViewAdapter<FillupViewHolder> {

    public FillupAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(FillupViewHolder viewHolder, Cursor cursor) {

    }

    @Override
    public FillupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
