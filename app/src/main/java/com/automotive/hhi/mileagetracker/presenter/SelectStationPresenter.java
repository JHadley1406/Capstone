package com.automotive.hhi.mileagetracker.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.view.AddFillupFragment;
import com.automotive.hhi.mileagetracker.view.SelectStationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
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
        mSelectStationView = view;
        mContext = mSelectStationView.getContext();
        mGoogleApiClient = new GoogleApiClient
                .Builder(mContext)
                .addApiIfAvailable(Places.PLACE_DETECTION_API)
                .build();
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

       mSelectStationView.showNearby(mStations);
    }

    public void loadUsedStations(){
        mSelectStationView.showUsed(mContext.getContentResolver()
                .query(DataContract.StationTable.CONTENT_URI
                        , null, null, null, null));
    }

    public void setCarId(int carId){
        mCarId = carId;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)mSelectStationView, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else{
            PendingResult<PlaceLikelihoodBuffer> nearbyStationBuffer = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
            nearbyStationBuffer.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    for(PlaceLikelihood likelyStation : placeLikelihoods){
                        if(likelyStation.getPlace().getPlaceTypes().contains(Place.TYPE_GAS_STATION)){
                            Station station = new Station();
                            station.setId(0);
                            station.setName(likelyStation.getPlace().getName().toString());
                            station.setAddress(likelyStation.getPlace().getAddress().toString());
                            station.setLat(likelyStation.getPlace().getLatLng().latitude);
                            station.setLon(likelyStation.getPlace().getLatLng().longitude);
                            mStations.add(station);
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

    public void prepareUsedStaionsRv(RecyclerView recyclerView){
        StationAdapter usedAdapter = new StationAdapter(mContext, null, this);
        recyclerView.setAdapter(usedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }


    public void prepareNearbyStationRv(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }


    @Override
    public void onClick(Station station) {
        mSelectStationView.addFillup(mCarId, station);
    }
}
