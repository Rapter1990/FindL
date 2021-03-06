package com.germiyanoglu.android.findl.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.FavoriteLocationListItemAdapter;
import com.germiyanoglu.android.findl.bottomnavigationmenu.BottomNavigationBar;
import com.germiyanoglu.android.findl.data.LocationDetailContract;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.widget.FavoriteLocationProvider;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 277) Creating FavoriteLocationActivity to list favorite location
public class FavoriteLocationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = FavoriteLocationActivity.class.getName();

    private Context mContext;

    // TODO : 278) Defining attributes from activity_favorite_location
    @BindView(R.id.favorite_location_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.favorite_location_list_recycler_view)
    RecyclerView favoriteLocationRecyleView;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx mBottomNavigationViewEx;

    // TODO : 281) Defining variables
    public static final int FAVOURITE_LOCATION_DETAIL_LOADER = 100;
    private static final int ICON_NUMBER_MENU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_location);
        ButterKnife.bind(this);
        mContext = FavoriteLocationActivity.this;
        bottomNavigationViewMenu();
        defineToolbar();
        defineLayout();


        // TODO : 284) Initialize loader Manager
        getSupportLoaderManager().initLoader(FAVOURITE_LOCATION_DETAIL_LOADER, null,
                FavoriteLocationActivity.this);

    }


    private void defineToolbar() {
        // TODO : 279) Defining Toolbar
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.favourite_location_list));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void defineLayout() {
        // TODO : 282) Defining layout for listing favorite location
        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.gridlayout_location_list_span);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, span, orientation, reverseLayout);
        FavoriteLocationListItemAdapter mFavoriteLocationListAdapter = new FavoriteLocationListItemAdapter(mContext, null);
        favoriteLocationRecyleView.setLayoutManager(layoutManager);
        favoriteLocationRecyleView.setAdapter(mFavoriteLocationListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // TODO : 283) Defining Loader Manager Method to load favorite location from database
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //define projection for the favouritePlace Details (No of Column)
        String[] projection = {
                LocationDetailContract.LocationDetailEntry._ID,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LATITUDE,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LONGITUDE,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ADDRESS,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_PHONE_NUMBER,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_WEBSITE,
                LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_SHARE_LINK
        };

        return new CursorLoader(mContext,
                LocationDetailContract.LocationDetailEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished called with data");
        ((FavoriteLocationListItemAdapter) favoriteLocationRecyleView.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        ((FavoriteLocationListItemAdapter) favoriteLocationRecyleView.getAdapter()).swapCursor(null);
    }


    // TODO : 299 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu() {
        Log.d(TAG, "bottomNavigationViewMenu is starting");
        BottomNavigationBar.setBottomBottomNavigationBar(mBottomNavigationViewEx);

        // TODO 300 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBar.navitageIcon(this, this, mBottomNavigationViewEx);

        // TODO 301 ) Showing Icon List
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }
}
