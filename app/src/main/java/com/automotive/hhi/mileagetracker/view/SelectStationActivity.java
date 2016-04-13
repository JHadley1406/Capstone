package com.automotive.hhi.mileagetracker.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.LocBasedStationAdapter;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.SelectStationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStationActivity extends AppCompatActivity implements SelectStationView
        , AddFillupFragment.OnFragmentInteractionListener {

    private final int PERMISSION_REQUEST_CODE = 100;

    @Bind(R.id.select_station_nearby_rv)
    public RecyclerView mNearbyStationRV;
    @Bind(R.id.select_station_used_rv)
    public RecyclerView mUsedStationRV;
    @Bind(R.id.select_station_toolbar)
    public Toolbar mToolbar;
    private SelectStationPresenter mSelectStationPresenter;
    private AddFillupFragment mAddFillupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUsedStationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mNearbyStationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        preparePresenter();
        checkPermission();
    }

    @Override
    public void showNearby(LocBasedStationAdapter stations) {
        mNearbyStationRV.setAdapter(stations);
    }

    @Override
    public void showUsed(StationAdapter stations) {
        mUsedStationRV.setAdapter(stations);
        stations.notifyDataSetChanged();
    }

    @Override
    public void addFillup(long carId, Station station) {
        mAddFillupFragment = AddFillupFragment.newInstance(carId, station);
        mAddFillupFragment.show(getFragmentManager(), "add_fillup_fragment");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_station_select, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case 1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext()
                , android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this
                    , new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}
                    , PERMISSION_REQUEST_CODE);
        } else{
            mSelectStationPresenter.getNearbyStations();
        }
    }

    private void preparePresenter(){
        mSelectStationPresenter = new SelectStationPresenter(getApplicationContext(), getLoaderManager());
        mSelectStationPresenter.attachView(this);
        mSelectStationPresenter.setCarId(getIntent().getLongExtra(IntentContract.CAR_ID, 1));

    }

    @Override
    public void onFragmentInteraction() {
        mAddFillupFragment.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mSelectStationPresenter.getNearbyStations();

        }
    }
}
