package com.automotive.hhi.mileagetracker.dagger.components;

import com.automotive.hhi.mileagetracker.dagger.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Josiah Hadley on 3/25/2016.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


}
