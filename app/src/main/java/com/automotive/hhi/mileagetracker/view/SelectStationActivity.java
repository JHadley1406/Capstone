package com.automotive.hhi.mileagetracker.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.automotive.hhi.mileagetracker.KeyContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.LocBasedStationAdapter;
import com.automotive.hhi.mileagetracker.adapters.StationAdapter;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.SelectStationPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectStationActivity extends AppCompatActivity implements SelectStationView
        , AddFillupFragment.OnFillupFragmentInteractionListener {

    private final int PERMISSION_REQUEST_CODE = 100;

    @Bind(R.id.select_station_address_input)
    public EditText mAddressSearch;
    @Bind(R.id.select_station_address_find_button)
    public Button mAddressSearchButton;
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

    @OnClick(R.id.select_station_address_find_button)
    public void addressSearch(){
        mSelectStationPresenter.addressSearch(mAddressSearch.getText().toString());
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
    public void addFillup(Car car, Station station) {
        mAddFillupFragment = AddFillupFragment.newInstance(car, station, new Fillup(), false);
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
            case android.R.id.home:{
                NavUtils.navigateUpTo(this, mSelectStationPresenter.returnToCarDetailIntent());
                return true;
            }
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
            mSelectStationPresenter.updateLocation();
        }
    }

    private void preparePresenter(){
        mSelectStationPresenter = new SelectStationPresenter(getApplicationContext(), getLoaderManager());
        mSelectStationPresenter.attachView(this);
        mSelectStationPresenter.setCar((Car) getIntent().getParcelableExtra(KeyContract.CAR));

    }

    @Override
    public void onFillupFragmentInteraction() {
        mAddFillupFragment.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mSelectStationPresenter.updateLocation();

        }
    }

    @Override
    public void launchGPSAlert(){
        AlertDialog.Builder gpsAlertDialog = new AlertDialog.Builder(this);
        gpsAlertDialog.setMessage(R.string.gps_alert_message)
                .setCancelable(false)
                .setPositiveButton(R.string.gps_alert_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.gps_alert_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }
}
