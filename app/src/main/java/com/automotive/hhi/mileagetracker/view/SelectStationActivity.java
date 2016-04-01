package com.automotive.hhi.mileagetracker.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.SelectStationPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStationActivity extends AppCompatActivity implements SelectStationView {

    @Bind(R.id.select_station_nearby_rv)
    private RecyclerView mNearbyStationRV;
    @Bind(R.id.select_station_used_rv)
    private RecyclerView mUsedStationRV;
    @Bind(R.id.select_station_toolbar)
    private Toolbar mToolbar;
    private SelectStationPresenter mSelectStationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prepareRecyclerViews();
        preparePresenter();

    }

    @Override
    public void showNearby(Cursor stations) {
        if(stations.moveToFirst()){
            StationAdapter adapter = (StationAdapter) mNearbyStationRV.getAdapter();
            adapter.changeCursor(stations);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showUsed(Cursor stations) {
        if(stations.moveToFirst()){
            StationAdapter adapter = (StationAdapter) mUsedStationRV.getAdapter();
            adapter.changeCursor(stations);
            adapter.notifyDataSetChanged();
        }
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

    private void prepareRecyclerViews(){
        StationAdapter nearbyAdapter = new StationAdapter(getContext(), null);
        StationAdapter usedAdapter = new StationAdapter(getContext(), null);
        mNearbyStationRV.setAdapter(nearbyAdapter);
        mUsedStationRV.setAdapter(usedAdapter);
        mNearbyStationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsedStationRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void preparePresenter(){
        mSelectStationPresenter = new SelectStationPresenter();
        mSelectStationPresenter.attachView(this);
        mSelectStationPresenter.loadNearbyStations();
        mSelectStationPresenter.loadUsedStations();
    }
}
