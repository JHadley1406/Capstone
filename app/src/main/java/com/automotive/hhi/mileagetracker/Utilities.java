package com.automotive.hhi.mileagetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;

/**
 * Created by Josiah Hadley on 3/28/2016.
 */
public class Utilities {

    Context mContext;

    public Utilities(Context context){
        mContext = context;
    }

    public Fillup fillupFromCursor(Cursor cursor){
        Fillup fillup = new Fillup();


        fillup.setCar(carFromCursor(mContext
                .getContentResolver()
                .query(DataContract.CarTable.CONTENT_URI
                        , null
                        , DataContract.CarTable._ID + " = " + cursor.getInt(cursor
                            .getColumnIndexOrThrow(DataContract.FillupTable.CAR))
                        , null, null)));
        fillup.setStation(stationFromCursor(mContext
                .getContentResolver()
                .query(DataContract.StationTable.CONTENT_URI
                        , null
                        , DataContract.StationTable._ID + "=" + cursor.getInt(cursor
                            .getColumnIndexOrThrow(DataContract.FillupTable.STATION))
                        , null, null)));

        fillup.setId(cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable._ID)));
        fillup.setDate(cursor
                .getLong(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.DATE)));
        fillup.setFillupMileage(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.MILEAGE)));
        fillup.setFillupMpg(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.MPG)));
        fillup.setFuelCost(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.COST)));
        fillup.setGallons(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.GALLONS)));
        fillup.setOctane(cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(DataContract.FillupTable.OCTANE)));
        return fillup;
    }

    public ContentValues fillupToContentValues(Fillup fillup){
        ContentValues vals = new ContentValues();
        vals.put(DataContract.FillupTable.CAR, fillup.getCar().getId());
        vals.put(DataContract.FillupTable.STATION, fillup.getStation().getId());

        vals.put(DataContract.FillupTable.COST, fillup.getFuelCost());
        vals.put(DataContract.FillupTable.DATE, fillup.getDate());
        vals.put(DataContract.FillupTable.GALLONS, fillup.getGallons());
        vals.put(DataContract.FillupTable.MILEAGE, fillup.getFillupMileage());
        vals.put(DataContract.FillupTable.MPG, fillup.getFillupMpg());
        vals.put(DataContract.FillupTable.OCTANE, fillup.getOctane());

        return vals;
    }

    public static Station stationFromCursor(Cursor cursor){
        Station station = new Station();

        station.setId(cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable._ID)));
        station.setAddress(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.ADDRESS)));
        station.setCity(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.CITY)));
        station.setState(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.STATE)));
        station.setName(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.NAME)));
        station.setZip(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.ZIP)));
        station.setLat(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.LAT)));
        station.setLon(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.StationTable.LON)));

        return station;
    }

    public static ContentValues stationToContentValues(Station station){
        ContentValues vals = new ContentValues();
        vals.put(DataContract.StationTable.ADDRESS, station.getAddress());
        vals.put(DataContract.StationTable.CITY, station.getCity());
        vals.put(DataContract.StationTable.STATE, station.getState());
        vals.put(DataContract.StationTable.NAME, station.getName());
        vals.put(DataContract.StationTable.ZIP, station.getZip());
        vals.put(DataContract.StationTable.LAT, station.getLat());
        vals.put(DataContract.StationTable.LON, station.getLon());
        return vals;
    }

    public Car carFromCursor(Cursor cursor){
        Car car = new Car();
        car.setAvgMpg(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.AVGMPG)));
        car.setId(cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable._ID)));
        car.setMake(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.MAKE)));
        car.setModel(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.MODEL)));
        car.setName(cursor
                .getString(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.NAME)));
        car.setYear(cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.YEAR)));
        car.setStartingMileage(cursor
                .getDouble(cursor
                        .getColumnIndexOrThrow(DataContract.CarTable.INITMILES)));
        return car;
    }

    public ContentValues carToContentValues(Car car){
        ContentValues vals = new ContentValues();
        vals.put(DataContract.CarTable.AVGMPG, car.getAvgMpg());
        vals.put(DataContract.CarTable.INITMILES, car.getStartingMileage());
        vals.put(DataContract.CarTable.MAKE, car.getMake());
        vals.put(DataContract.CarTable.MODEL, car.getModel());
        vals.put(DataContract.CarTable.NAME, car.getName());
        vals.put(DataContract.CarTable.YEAR, car.getYear());

        return vals;
    }
}
