package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.LocationItemListAdapter;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationListActivity extends AppCompatActivity implements LocationItemListAdapter.NearLocationListItemAdapterOnClickHandler{

    private static final String TAG = LocationListOnMapActivity.class.getName();

    private Context mContext;

    // TODO : 249) Defining attributes from activity_location_list
    @BindView(R.id.location_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.location_list_recycler_view)
    RecyclerView locationListRecyleView;

    @BindView(R.id.location_list_empty_textview)
    TextView emptyTextView;

    private String mLocationQueryStringUrl = "";
    private ArrayList<Location> mNearByLocationArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationListActivity.this;
        defineToolbar();
        defineLocationTag();

        // TODO : 256 ) Getting location tag from tocImage in LocationListOnMapActivity
        mNearByLocationArrayList = getIntent().getParcelableArrayListExtra(GoogleMapApi.ALL_NEARBY_LOCATION);

        if (mNearByLocationArrayList.size() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
            locationListRecyleView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            locationListRecyleView.setVisibility(View.VISIBLE);

            int orientation = GridLayout.VERTICAL;
            int span = getResources().getInteger(R.integer.gridlayout_location_list_span);
            boolean reverseLayout = false;
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, span, orientation, reverseLayout);
            LocationItemListAdapter mLocationListAdapter = new LocationItemListAdapter(mContext, mNearByLocationArrayList,this);
            locationListRecyleView.setLayoutManager(layoutManager);
            locationListRecyleView.setAdapter(mLocationListAdapter);
        }
    }

    private void defineLocationTag() {
        // TODO : 254 ) Getting location tag from LocationListOnMapActivity
        String locationTag = getIntent().getStringExtra(GoogleMapApi.LOCATION_TYPE_EXTRA_TEXT);

        String currentLocation = mContext.getSharedPreferences(
                GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);

        Log.d(TAG, "Current Location : " + currentLocation);

        // TODO : 255 ) Determining Location Url
        mLocationQueryStringUrl = GoogleMapApi.BASE_URL + GoogleMapApi.NEARBY_SEARCH_TAG + "/" +
                GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_TAG + "=" +
                currentLocation + "&" + GoogleMapApi.RADIUS_TAG + "=" +
                GoogleMapApi.RADIUS_VALUE + "&" + GoogleMapApi.PLACE_TYPE_TAG + "=" + locationTag +
                "&" + GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY;

        Log.d(TAG, "Location URL: " + mLocationQueryStringUrl);
    }

    private void defineToolbar() {
        // TODO : 251) Getting location name from LocationListOnMapActivity
        String locationName = getIntent().getStringExtra(GoogleMapApi.LOCATION_NAME_EXTRA_TEXT);

        // TODO : 252) Defining Toolbar
        setSupportActionBar(toolbar);
        String actionBarTitleText = getResources().getString(R.string.show_result) +
                " " + locationName + " " + getString(R.string.near);
        setTitle(actionBarTitleText);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO : 253 ) Returning back from Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO : 257 ) By clicking any location, navigating to its location detail
    @Override
    public void onClick(Location nearLocationData) {
        if (UtilMethods.isNetworkAvailable(mContext)) {
            Intent currentLocationDetailIntent = new Intent(mContext, LocationDetailActivity.class);
            currentLocationDetailIntent.putExtra(GoogleMapApi.LOCATION_ID_EXTRA_TEXT,
                    nearLocationData.getmLocationId());
            mContext.startActivity(currentLocationDetailIntent);

        } else
            Toast.makeText(mContext,getResources().getString(R.string.no_connection),Toast.LENGTH_SHORT).show();
    }
}
