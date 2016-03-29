package com.automotive.hhi.mileagetracker;

import android.app.Application;

import com.automotive.hhi.mileagetracker.dagger.components.ApplicationComponent;
import com.automotive.hhi.mileagetracker.dagger.modules.ApplicationModule;

/**
 * Created by Josiah Hadley on 3/25/2016.
 */
public class TrackerApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }
}
