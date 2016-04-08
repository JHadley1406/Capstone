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

import com.automotive.hhi.mileagetracker.IntentContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.LocBasedStationAdapter;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.SelectStationPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectStationActivity extends AppCompatActivity implements SelectStationView
        , AddFillupFragment.OnFragmentInteractionListener {

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
        mUsedStationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mNearbyStationRV.setLayoutManager(new LinearLayoutManager(getContext()));

        preparePresenter();

    }

    @Override
    public void showNearby(LocBasedStationAdapter stations) {
        mNearbyStationRV.setAdapter(stations);
    }

    @Override
    public void showUsed(StationAdapter stations) {
        mUsedStationRV.setAdapter(stations);
    }

    @Override
    public void addFillup(int carId, Station station) {
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

    private void preparePresenter(){
        mSelectStationPresenter = new SelectStationPresenter();
        mSelectStationPresenter.attachView(this);
        mSelectStationPresenter.setCarId(getIntent().getIntExtra(IntentContract.CAR_ID, 1));
        mSelectStationPresenter.loadNearbyStations();
        mSelectStationPresenter.loadUsedStations();

    }

    @Override
    public void onFragmentInteraction() {
        mAddFillupFragment.dismiss();
    }
}
