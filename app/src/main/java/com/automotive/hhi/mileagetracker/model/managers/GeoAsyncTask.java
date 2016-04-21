package com.automotive.hhi.mileagetracker.model.managers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Josiah Hadley on 4/20/2016.
 */
public class GeoAsyncTask extends AsyncTask<String, Void, LatLng> {

    private final String LOG_TAG = GeoAsyncTask.class.getSimpleName();
    private Context mContext;

    public GeoAsyncTask(Context context, ){
        mContext = context;
    }

    @Override
    protected LatLng doInBackground(String... params) {

        try {
            Geocoder geocoder = new Geocoder(mContext);
            List<Address> locations = geocoder.getFromLocationName(params[0], 1);
            return new LatLng(locations.get(0).getLatitude(), locations.get(0).getLatitude());
        } catch(IOException e){
            Log.e(LOG_TAG, "IOException CaughtL : " + e.toString());
        }
        return null;
    }

    @Override
    public void onPostExecute(LatLng location){

    }

}
