package com.automotive.hhi.mileagetracker.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Station;

import java.util.List;

public class SelectStationActivity extends AppCompatActivity implements SelectStationView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showNearby(List<Station> stations) {

    }

    @Override
    public void showUsed(List<Station> stations) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
