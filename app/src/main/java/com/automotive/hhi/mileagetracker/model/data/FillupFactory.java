package com.automotive.hhi.mileagetracker.model.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class FillupFactory extends Fillup {

    public Fillup fromCursor(Cursor cursor){
        Fillup fillup = new Fillup();

        return fillup;
    }

    public ContentValues toContentValues(Fillup fillup){
        ContentValues vals = new ContentValues();


        return vals;
    }
}
