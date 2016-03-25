package com.automotive.hhi.mileagetracker.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mtracker.db";
    private static final int DB_VER = 1;
    private Context mContext;

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.CAR_TABLE);
        db.execSQL(DataContract.STATION_TABLE);
        db.execSQL(DataContract.FILLUP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.CAR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.STATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.FILLUP_TABLE);
        onCreate(db);
    }
}