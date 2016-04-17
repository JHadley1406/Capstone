package com.automotive.hhi.mileagetracker.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.KeyContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.FillupAdapter;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.presenter.CarDetailPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarDetailActivity extends AppCompatActivity implements CarDetailView
        , AddCarFragment.OnCarFragmentInteractionListener
        , AddFillupFragment.OnFillupFragmentInteractionListener {

    private final String LOG_TAG = CarDetailActivity.class.getSimpleName();

    @Bind(R.id.car_detail_name)
    public TextView mCarName;
    @Bind(R.id.car_detail_make)
    public TextView mCarMake;
    @Bind(R.id.car_detail_model)
    public TextView mCarModel;
    @Bind(R.id.car_detail_year)
    public TextView mCarYear;
    @Bind(R.id.car_detail_avg_mpg)
    public TextView mAverageMpg;
    @Bind(R.id.car_detail_fillups_rv)
    public RecyclerView mFillupRecyclerView;
    @Bind(R.id.car_detail_delete_car)
    public Button mDeleteCar;
    @Bind(R.id.car_detail_edit_car)
    public Button mEditCar;
    @Bind(R.id.car_detail_add_fillup)
    public Button mAddFillup;
    @Bind(R.id.car_detail_toolbar)
    public Toolbar mToolbar;
    private CarDetailPresenter mCarDetailPresenter;
    private AddCarFragment mEditCarFragment;
    private AddFillupFragment mEditFillupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFillupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        preparePresenter();

    }

    @OnClick(R.id.car_detail_add_fillup)
    public void onClick(){
        mCarDetailPresenter.launchSelectStation();

    }

    @OnClick(R.id.car_detail_edit_car)
    public void editCar(){
        mCarDetailPresenter.launchEditCar();
    }

    @Override
    public void launchSelectStation(Intent selectStationIntent) {
        startActivityForResult(selectStationIntent, KeyContract.DETAIL_TO_STATION_CODE);
    }

    @Override
    public void launchEditCar(Car car){
        mEditCarFragment = AddCarFragment.newInstance(car, true);
        mEditCarFragment.show(getFragmentManager(), "edit_car_fragment");
    }

    @Override
    public void launchEditFillup(Car car, Station station, Fillup fillup){
        mEditFillupFragment = AddFillupFragment.newInstance(car, station, fillup);
        mEditFillupFragment.show(getFragmentManager(), "edit_fillup_fragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == KeyContract.DETAIL_TO_STATION_CODE){
            if(resultCode == RESULT_OK){

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.car_detail_menu_car_list:
            {
                startActivity(new Intent(getContext(), CarListActivity.class));
            }
            case R.id.action_settings: {
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFillups(FillupAdapter fillups) {
        Log.i(LOG_TAG, "Showing fillups");
        mFillupRecyclerView.setAdapter(fillups);
        fillups.notifyDataSetChanged();
    }

    @Override
    public void showCar(Car car) {
        mAverageMpg.setText(String.format("%.1f", car.getAvgMpg()));
        mCarName.setText(car.getName());
        mCarMake.setText(car.getMake());
        mCarModel.setText(car.getModel());
        mCarYear.setText(String.format("%d", car.getYear()));
    }



    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    private void preparePresenter(){
        mCarDetailPresenter = new CarDetailPresenter(getApplicationContext()
                , getLoaderManager()
                , (Car)getIntent().getParcelableExtra(KeyContract.CAR));
        mCarDetailPresenter.attachView(this);
        mCarDetailPresenter.loadCar();

    }

    @Override
    public void onCarFragmentInteraction(Car car) {
        mEditCarFragment.dismiss();
        mCarDetailPresenter.updateCar(car);

    }

    @Override
    public void onFillupFragmentInteraction() {
        mEditFillupFragment.dismiss();
        mFillupRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
