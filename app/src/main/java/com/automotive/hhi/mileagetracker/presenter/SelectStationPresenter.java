package com.automotive.hhi.mileagetracker.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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
import com.automotive.hhi.mileagetracker.model.data.StationFactory;
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
        , ViewHolderOnClickListener<Station>
        , LoaderManager.LoaderCallbacks<Cursor>{

    private final String LOG_TAG = SelectStationPresenter.class.getSimpleName();
    private final int USED_STATION_LOADER_ID = 837294056;



    private SelectStationView mSelectStationView;
    private LocBasedStationAdapter mNearbyAdapter;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private List<Station> mStations;
    private StationAdapter mStationAdapter;
    private LoaderManager mLoaderManager;

    public long mCarId;

    public SelectStationPresenter(Context context, LoaderManager loaderManager){
        mContext = context;
        mLoaderManager = loaderManager;
        mStations = new ArrayList<>();
        mStationAdapter = new StationAdapter(mContext, null, this);
    }

    @Override
    public void attachView(SelectStationView view) {
        mSelectStationView = view;
        buildGoogleApiClient();
        mNearbyAdapter = new LocBasedStationAdapter(mStations, this);
        loadNearbyStations();
        mLoaderManager.initLoader(USED_STATION_LOADER_ID, null, this);
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
        mSelectStationView.showNearby(mNearbyAdapter);
    }

    private void updateNearbyStations(){
        mNearbyAdapter.updateStations(mStations);
    }

    //TODO: Remove after testing is done
    private void insertTestData(){
        Station station = new Station();
        station.setLat(9999);
        station.setLon(9999);
        station.setName("Test Station");
        station.setAddress("123 Test St. San Jose CA, 95113");
        mContext.getContentResolver()
                .insert(DataContract.StationTable.CONTENT_URI
                        , StationFactory.toContentValues(station));
    }

    public void setCarId(long carId){
        mCarId = carId;
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            PendingResult<PlaceLikelihoodBuffer> nearbyStationBuffer =
                    Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
            nearbyStationBuffer.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    for (PlaceLikelihood likelyStation : placeLikelihoods) {
                        if (likelyStation
                                .getPlace()
                                .getPlaceTypes()
                                .contains(Place.TYPE_GAS_STATION)) {
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
                    updateNearbyStations();
                }
            });
        } catch (SecurityException e){
            Log.e(LOG_TAG, "Security Exception : " + e.toString());
        }
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext
                , DataContract.StationTable.CONTENT_URI
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null || !data.moveToFirst()){
            insertTestData();
        }
        mStationAdapter.changeCursor(data);
        mSelectStationView.showUsed(mStationAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoaderManager.restartLoader(USED_STATION_LOADER_ID, null, this);
    }

    private void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient
                .Builder(mContext)
                .addApiIfAvailable(LocationServices.API)
                .addApiIfAvailable(Places.GEO_DATA_API)
                .addApiIfAvailable(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    public void getNearbyStations(){
        if(mGoogleApiClient == null){
            buildGoogleApiClient();
        }
        mGoogleApiClient.connect();
    }

}
