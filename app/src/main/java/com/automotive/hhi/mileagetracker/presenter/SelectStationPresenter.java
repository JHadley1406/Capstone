package com.automotive.hhi.mileagetracker.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.automotive.hhi.mileagetracker.adapters.LocBasedStationAdapter;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.AddFillupFragment;
import com.automotive.hhi.mileagetracker.view.SelectStationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;


/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public class SelectStationPresenter implements Presenter<SelectStationView>
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , StationOnClickListener {

    private final String LOG_TAG = SelectStationPresenter.class.getSimpleName();

    private final int PERMISSION_REQUEST_CODE = 100;

    private SelectStationView mSelectStationView;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private List<Station> mStations;

    public int mCarId;

    public SelectStationPresenter(){
        mStations = new ArrayList<>();
    }

    @Override
    public void attachView(SelectStationView view) {
        Log.i(LOG_TAG, "In attachView");
        mSelectStationView = view;
        mContext = mSelectStationView.getContext();
        mGoogleApiClient = new GoogleApiClient
                .Builder(mContext)
                .addApiIfAvailable(LocationServices.API)
                .addApiIfAvailable(Places.GEO_DATA_API)
                .addApiIfAvailable(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.i(LOG_TAG, "Connecting to Google Api Client");
        mGoogleApiClient.connect();
    }

    @Override
    public void detachView() {
        mSelectStationView = null;
        mContext = null;
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    public void loadNearbyStations(){
        Log.i(LOG_TAG, "In loadNearbyStations");
        LocBasedStationAdapter adapter = new LocBasedStationAdapter(mStations, this);
        mSelectStationView.showNearby(adapter);
    }

    public void loadUsedStations(){
        Log.i(LOG_TAG, "In loadUsedStations");
        Cursor usedStationCursor = mContext.getContentResolver()
                .query(DataContract.StationTable.CONTENT_URI
                        , null, null, null, null);
        StationAdapter adapter = new StationAdapter(mContext, usedStationCursor, this);
        mSelectStationView.showUsed(adapter);
    }

    public void setCarId(int carId){
        mCarId = carId;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOG_TAG, "In onConnected");
        if(ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)mSelectStationView, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else{
            PendingResult<PlaceLikelihoodBuffer> nearbyStationBuffer = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
            nearbyStationBuffer.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    Log.i(LOG_TAG, "in nearbyStationBuffer onResult");
                    Log.i(LOG_TAG, "Returned : " + placeLikelihoods.getCount());
                    for(PlaceLikelihood likelyStation : placeLikelihoods){
                        if(likelyStation.getPlace().getPlaceTypes().contains(Place.TYPE_GAS_STATION)){
                            Log.i(LOG_TAG, "Stations Exist");
                            Log.i(LOG_TAG, likelyStation.getPlace().getName().toString());
                            Station station = new Station();
                            station.setId(0);
                            station.setName(likelyStation.getPlace().getName().toString());
                            station.setAddress(likelyStation.getPlace().getAddress().toString());
                            station.setLat(likelyStation.getPlace().getLatLng().latitude);
                            station.setLon(likelyStation.getPlace().getLatLng().longitude);
                            mStations.add(station);
                        } else{
                            Log.i(LOG_TAG, "Not a gas station");
                            Log.i(LOG_TAG, "Place name: " + likelyStation.getPlace().getName().toString());
                            Log.i(LOG_TAG, "Place Types: " + likelyStation.getPlace().getPlaceTypes());
                        }
                    }
                    placeLikelihoods.release();
                }
            });
        }
        loadNearbyStations();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Connection Failed : " + connectionResult.toString());
    }

    @Override
    public void onClick(Station station) {
        mSelectStationView.addFillup(mCarId, station);
    }
}
