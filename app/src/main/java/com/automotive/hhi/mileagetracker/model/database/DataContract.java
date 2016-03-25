package com.automotive.hhi.mileagetracker.model.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.automotive.hhi.mileagetracker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CARS = "cars";
    public static final String PATH_FILLUPS = "fillups";
    public static final String PATH_STATIONS = "stations";

    public static final class CarTable implements BaseColumns{
        public static final String NAME = "name";
        public static final String MAKE = "make";
        public static final String MODEL = "model";
        public static final String YEAR = "year";
        public static final String INITMILES = "initialmileage";
        public static final String AVGMPG = "avgmpg";

        public static Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CARS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CARS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CARS;

        public static Uri buildCarWithId(){
            return CONTENT_URI.buildUpon().appendPath(_ID).build();
        }
    }

    public static final class FillupTable implements BaseColumns{
        public static final String CAR = "car";
        public static final String MILEAGE = "mileage";
        public static final String MPG = "mpg";
        public static final String STATION = "station";
        public static final String GALLONS = "gallons";
        public static final String OCTANE = "octane";
        public static final String COST = "cost";
        public static final String DATE = "date";

        public static Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILLUPS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILLUPS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILLUPS;

        public static Uri buildFillupWithId(){
            return CONTENT_URI.buildUpon().appendPath(_ID).build();
        }

        public static Uri buildFillupWithCar(){
            return CONTENT_URI.buildUpon().appendPath(CAR).build();
        }

        public static Uri buildFillupWithStation(){
            return CONTENT_URI.buildUpon().appendPath(STATION).build();
        }
    }

    public static final class StationTable implements BaseColumns{
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String ZIP = "zip";
        public static final String LAT = "lat";
        public static final String LON = "lon";

        public static Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATIONS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATIONS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATIONS;

        public static Uri buildStationWithId(){
            return CONTENT_URI.buildUpon().appendPath(_ID).build();
        }
        
    }

}
