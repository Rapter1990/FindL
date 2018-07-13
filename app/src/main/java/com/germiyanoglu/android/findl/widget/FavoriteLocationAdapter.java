package com.germiyanoglu.android.findl.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.activity.LocationDetailActivity;
import com.germiyanoglu.android.findl.data.LocationDetailContract;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.google.gson.Gson;

import java.util.ArrayList;

// TODO 307 ) Defining FavoriteLocationAdapter to define location to be listed in listview
public class FavoriteLocationAdapter implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = FavoriteLocationAdapter.class.getName();

    private static final String PREFS_NAME = "FavoriteLocationWidget";
    private static final String PREF_PREFIX_KEY = "locationWidget";
    private static final String PREF_LOCATION_KEY = "currentLocation";

    int appwidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // TODO 312 ) Defining context and location arraylist
    private Context mContext;
    private ArrayList<Location> mFavouriteLocationWidgetArrayList = new ArrayList<>();

    public FavoriteLocationAdapter(Context context) {
        mContext = context;
        Log.d(TAG,"Context : " + mContext);
    }

    @Override
    public void onCreate() {
        mFavouriteLocationWidgetArrayList = getFavoriteLocationArrayList();

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mFavouriteLocationWidgetArrayList == null){
            return 0;
        }
        return mFavouriteLocationWidgetArrayList.size();
    }

    // TODO 314 ) Listing favorite Location
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.favorite_location_list_widget);

        Log.d(TAG,"getViewAt / Favorite Location Size : " + mFavouriteLocationWidgetArrayList.size());

        Location favoriteLocation = mFavouriteLocationWidgetArrayList.get(position);
        Log.d(TAG,"getViewAt / Location information : " + favoriteLocation.toString());

        remoteViews.setTextViewText(R.id.favorite_widget_location_name, favoriteLocation.getmLocationName());
        remoteViews.setTextViewText(R.id.favorite_widget_location_address, favoriteLocation.getmLocationAddress());
        remoteViews.setTextViewText(R.id.favorite_widget_location_open_status, favoriteLocation.getmLocationOpeningHourStatus());

        Log.d(TAG,"getViewAt / Location name : " + favoriteLocation.getmLocationName());
        Log.d(TAG,"getViewAt / Location Address : " + favoriteLocation.getmLocationAddress());
        Log.d(TAG,"getViewAt / Location Open Status :        " + favoriteLocation.getmLocationOpeningHourStatus());
        Log.d(TAG,"getViewAt / Location Id : " + favoriteLocation.getmLocationId());


//        Intent currentLocationDetailIntent = new Intent(mContext, LocationDetailActivity.class);
//        Log.d(TAG,"getViewAt / Location Id : " + favoriteLocation.getmLocationId());
//        currentLocationDetailIntent.putExtra(GoogleMapApi.LOCATION_ID_EXTRA_TEXT,
//                favoriteLocation.getmLocationId());
//        remoteViews.setOnClickFillInIntent(R.id.favorite_location_widget_list_view,currentLocationDetailIntent);



        Bundle extras = new Bundle();
        if (extras != null) {
            appwidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        saveLocationIdPreference(mContext,
                appwidgetId,
                favoriteLocation.getmLocationId(),
                favoriteLocation);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.location_list_widget_item, fillInIntent);

        Log.d(TAG,"getViewAt / LocationDetailActivity is opening ");



        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // TODO 313 ) Getting favorite location from database
    private ArrayList<Location> getFavoriteLocationArrayList(){

        ArrayList<Location> locationlist = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Cursor favoritelocationCursor = mContext.getContentResolver()
                    .query(LocationDetailContract.LocationDetailEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null,
                            null);

            if(favoritelocationCursor != null && favoritelocationCursor.getCount()>0) {
                favoritelocationCursor.moveToFirst();

                while(!favoritelocationCursor.isAfterLast()){

                    int locationColumnIndexOfId = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID);

                    int locationColumnIndexOfLatitude = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LATITUDE);

                    int locationColumnIndexOfLongitude = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LONGITUDE);

                    int locationColumnIndexOfName = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME);

                    int locationColumnIndexOfOpeningHourStatus = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS);

                    int locationColumnIndexOfRating = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING);

                    int locationColumnIndexOfAddress = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ADDRESS);

                    int locationColumnIndexOfPhoneNumber = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_PHONE_NUMBER);

                    int locationColumnIndexOfWebsite = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_WEBSITE);

                    int locationColumnIndexOfShareLink = favoritelocationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_SHARE_LINK);

                    String locationId = favoritelocationCursor.getString(locationColumnIndexOfId);
                    Double locationLatitude = favoritelocationCursor.getDouble(locationColumnIndexOfLatitude);
                    Double locationLongitude = favoritelocationCursor.getDouble(locationColumnIndexOfLongitude);
                    String locationName = favoritelocationCursor.getString(locationColumnIndexOfName);
                    String locationOpeningHourStatus = favoritelocationCursor.getString(locationColumnIndexOfOpeningHourStatus);
                    Double locationRating = favoritelocationCursor.getDouble(locationColumnIndexOfRating);
                    String locationAddress = favoritelocationCursor.getString(locationColumnIndexOfAddress);
                    String locationPhoneNumber = favoritelocationCursor.getString(locationColumnIndexOfPhoneNumber);
                    String locationWebsite = favoritelocationCursor.getString(locationColumnIndexOfWebsite);
                    String locationShareLink = favoritelocationCursor.getString(locationColumnIndexOfShareLink);

                    Location favoriteLocation = new Location();
                    favoriteLocation.setmLocationId(locationId);
                    favoriteLocation.setmLocationName(locationName);
                    favoriteLocation.setmLocationLatitude(locationLatitude);
                    favoriteLocation.setmLocationLongitude(locationLongitude);
                    favoriteLocation.setmLocationOpeningHourStatus(locationOpeningHourStatus);
                    favoriteLocation.setmLocationRating(locationRating);
                    favoriteLocation.setmLocationAddress(locationAddress);
                    favoriteLocation.setmLocationPhoneNumber(locationPhoneNumber);
                    favoriteLocation.setmLocationWebsite(locationWebsite);
                    favoriteLocation.setmLocationShareLink(locationShareLink);

                    Log.d(TAG,"getFavoriteLocationArrayList / Location information : " + favoriteLocation.toString());

                    locationlist.add(favoriteLocation);

                    favoritelocationCursor.moveToNext();
                }
            }

            favoritelocationCursor.close();
        }

        Log.d(TAG,"Favorite Location Size : " + locationlist.size());
        return locationlist;
    }

    public static void saveLocationIdPreference(Context context,int appWidgetId, String locationId, Location favoriteLocation){

        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, locationId);
        Gson gson = new Gson();
        String json = gson.toJson(favoriteLocation);
        prefs.putString(PREF_LOCATION_KEY + appWidgetId, json);
        prefs.apply();
        Log.d(TAG,"Location Id saved: " + locationId);

    }

    public static Location getCurrentLocationPreference(Context context , int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(PREF_LOCATION_KEY + appWidgetId, null);
        Gson gson = new Gson();
        Location currentLocation = gson.fromJson(json, Location.class);
        Log.d(TAG,"Current Location : " + currentLocation);
        return currentLocation;
    }



}
