package com.automotive.hhi.mileagetracker.view;

import java.util.List;

/**
 * Created by Josiah Hadley on 3/24/2016.
 */
public interface NavigationDrawerView extends MvpView {

    void showActions(List<String> actions);
}
