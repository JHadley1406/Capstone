package com.automotive.hhi.mileagetracker.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.automotive.hhi.mileagetracker.KeyContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.managers.CarListWidgetService;
import com.automotive.hhi.mileagetracker.presenter.WidgetPresenter;
import com.automotive.hhi.mileagetracker.view.CarDetailActivity;
import com.google.android.gms.common.api.PendingResult;

/**
 * Created by Josiah Hadley on 4/28/2016.
 */
public class CarListWidget extends AppWidgetProvider {

    private final String LOG_TAG = CarListWidget.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; ++i){
            Intent intent = new Intent(context, CarListWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.car_widget_list);
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.car_widget_list_view, intent);

            //remoteViews.setEmptyView(R.id.car_widget_list_view, R.id.car_widget_empty_view);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
