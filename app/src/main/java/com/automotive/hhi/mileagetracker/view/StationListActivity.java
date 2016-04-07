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
import com.automotive.hhi.mileagetracker.presenter.StationListPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StationListActivity extends AppCompatActivity implements StationListView {

    @Bind(R.id.station_list_fab)
    public FloatingActionButton mFab;
    @Bind(R.id.station_list_rv)
    public RecyclerView mStationRecyclerView;
    @Bind(R.id.station_list_toolbar)
    public Toolbar mToolbar;
    private StationListPresenter mStationListPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        prepareRecyclerView();

        preparePresenter();


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showStations(Cursor stations) {
        StationAdapter adapter = (StationAdapter)  mStationRecyclerView.getAdapter();
        adapter.changeCursor(stations);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_station_list, menu);
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

    public void prepareRecyclerView(){
        StationAdapter adapter = new StationAdapter(getContext(), null);
        mStationRecyclerView.setAdapter(adapter);
        mStationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void preparePresenter(){
        mStationListPresenter = new StationListPresenter();
        mStationListPresenter.attachView(this);
        mStationListPresenter.loadStations();

    }
}
