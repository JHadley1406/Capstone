package com.automotive.hhi.mileagetracker.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.adapters.CarAdapter;
import com.automotive.hhi.mileagetracker.presenter.CarListPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarListActivity extends AppCompatActivity implements CarListView, AddCarFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = CarListActivity.class.getSimpleName();
    private final int LOADER_ID = 12345123;

    @Bind(R.id.car_list_fab)
    public FloatingActionButton mFab;
    @Bind(R.id.car_list_rv)
    public RecyclerView mCarRecyclerView;
    @Bind(R.id.car_list_toolbar)
    public Toolbar mToolbar;
    private CarListPresenter mCarListPresenter;
    private AddCarFragment mAddCarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        preparePresenter();
        mCarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar();
            }
        });
    }

    @Override
    protected void onDestroy(){
        mCarListPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showCars(CarAdapter cars) {
        Log.i(LOG_TAG, "In showCars");
        mCarRecyclerView.setAdapter(cars);
        cars.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_car_list, menu);
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
        mCarListPresenter = new CarListPresenter();
        mCarListPresenter.attachView(this);
        getLoaderManager().initLoader(LOADER_ID, null, mCarListPresenter);
    }

    @Override
    public void onFragmentInteraction() {
        mAddCarFragment.dismiss();
        mCarRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void addCar(){
        mAddCarFragment = new AddCarFragment();
        mAddCarFragment.show(getFragmentManager(), "add_car_fragment");
    }

    @Override
    public void launchCarDetail(Intent carDetailIntent){
        startActivity(carDetailIntent);
    }

    @Override
    public LoaderManager getLoaderManager(){
        return getLoaderManager();
    }
}
