package com.germiyanoglu.android.findl.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.activity.LocationDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
// TODO 302 ) Defining Widget
public class FavoriteLocationProvider extends AppWidgetProvider {

    private static final String TAG = FavoriteLocationProvider.class.getName();

    // TODO 303 ) Updating Widget with WidgetService for updating listview and
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_location_provider);
        Log.d(TAG,"Constructing favorite_location_provider");

        // TODO 308 ) Defining list_view for location
        Intent serviceIntent = new Intent(context, FavoriteLocationWidgetService.class);
        views.setRemoteAdapter(R.id.favorite_location_list_view, serviceIntent);
        Log.d(TAG,"Defining FavoriteLocationWidgetService");

        // TODO 309 ) Sending location to its detail side
        Intent intent = new Intent(context, LocationDetailActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Log.d(TAG,"Sending location to its detail side");

        // TODO 310 ) Providing PendingIntent to work with its provider layout
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d(TAG,"Providing PendingIntent");

        // TODO 311 ) Giving onClick feature to pending intent
        views.setOnClickPendingIntent(R.id.favorite_location_relativelayout,pendingIntent);
        Log.d(TAG,"Adding onClick event");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
