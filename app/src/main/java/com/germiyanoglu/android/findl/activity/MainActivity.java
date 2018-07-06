package com.germiyanoglu.android.findl.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.LocationMainListItemAdapter;
import com.germiyanoglu.android.findl.bottomnavigationmenu.BottomNavigationBar;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.LocationArray;
import com.germiyanoglu.android.findl.utils.UtilMethods;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LocationMainListItemAdapter.LocationListItemAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 0;

    // TODO 30 ) Defining attributes of Main Activity
    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx mBottomNavigationViewEx;

    @BindView(R.id.main_screen_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_screen_cardview)
    CardView mCardView;

    @BindView(R.id.main_screen_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.main_screen_progress_bar)
    ProgressBar mProgressBar;

    private LinkedHashMap<String, Integer> locationList = new LinkedHashMap<>();
    private LocationMainListItemAdapter locationMainListItemAdapter;
    private Context mContext;
    private SharedPreferences mLocationSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = MainActivity.this;
        mToolbar.setTitle(getResources().getString(R.string.app_title));

        // TODO : 285) ------------------------------------------------------------------------------

        // TODO 35 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO : 146 ) Calling designLocationMainAdapter
        designLocationMainAdapter();
    }


    // TODO 34 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu() {
        Log.d(TAG, "bottomNavigationViewMenu is starting");
        BottomNavigationBar.setBottomBottomNavigationBar(mBottomNavigationViewEx);

        // TODO 35 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBar.navitageIcon(this, this, mBottomNavigationViewEx);

        // TODO 36 ) Showing Icon List
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }

    // TODO : 137 ) Adding SearchManager to Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu is working");
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, LocationSearchResultActivity.class)));

        return true;
    }


    // TODO : 138 ) Designing location list as a adapter
    private void designLocationMainAdapter() {
        Log.d(TAG, "designLocationMainAdapter is working");

        LocationArray locationArray = new LocationArray();
        locationList = locationArray.getLocationArray();

        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.gridlayout_span);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(this, span, orientation, reverseLayout);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        // TODO : 140 ) Defining LocationMainListItemAdapter
        locationMainListItemAdapter = new LocationMainListItemAdapter(getApplicationContext(), locationList, this);

        // TODO : 141 ) Set adapter to recyleview
        mRecyclerView.setAdapter(locationMainListItemAdapter);
    }

    // TODO : 142 ) Adding onClick to navigate to map with selected location
    @Override
    public void onClick(String locationIconName) {
        Log.d(TAG, "onClick is working");

        if (UtilMethods.isNetworkAvailable(getApplicationContext())) {
            // TODO : 143 ) Defining lcoation's name (such as "Airport")
            String lcoationName = locationIconName;

            // TODO : 144 ) Changing location Icon Name as below to send it to activity
            // because of using it in the query to get result (such as "airport")
            String locationTag = locationIconName.replace(' ', '_').toLowerCase();


            // TODO : 145 ) Calling Another activty to show location with map
            Intent locationListOnMap = new Intent(mContext, LocationListOnMapActivity.class);
            locationListOnMap.putExtra(GoogleMapApi.LOCATION_NAME_EXTRA_TEXT, lcoationName);
            locationListOnMap.putExtra(GoogleMapApi.LOCATION_TYPE_EXTRA_TEXT, locationTag);
            mContext.startActivity(locationListOnMap);
        }


    }
}
