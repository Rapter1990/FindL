package com.germiyanoglu.android.findl.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.LocationItemListAdapter;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.AppController;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationSearchResultActivity extends AppCompatActivity{

    private static final String TAG = LocationSearchResultActivity.class.getName();

    private Context mContext;

    private ArrayList<Location> mNearByLocationArrayList = new ArrayList<>();

    // TODO : 287) Defining attributes of lcaotino_search_result xml file
    @BindView(R.id.location_search_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.location_search_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.location_search_list_empty_textview)
    TextView nosearchTextView;

    @BindView(R.id.location_search_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_result);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationSearchResultActivity.this;

        progressBar.setVisibility(View.VISIBLE);
        defineToolbar();

        // TODO : 290) Getting query from Search Manager
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            Log.d(TAG, "Search Option is working");
            String locationType = getIntent().getStringExtra(SearchManager.QUERY);

            String currentLocation = mContext.getSharedPreferences(
                    GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                    .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);

            Log.d(TAG, "Current Location : " + currentLocation);

            String locationQueryStringUrl = GoogleMapApi.BASE_URL + GoogleMapApi.NEARBY_SEARCH_TAG + "/" +
                    GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_TAG + "=" +
                    currentLocation + "&" + GoogleMapApi.RANK_BY_TAG + "=" + GoogleMapApi.DISTANCE_TAG +
                    "&" + GoogleMapApi.KEYWORD_TAG + "=" + locationType.replace(" ", "") + "&" +
                    GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY;
            Log.d(TAG, "Location Query Url : " + locationQueryStringUrl);

            getSearchResult(locationQueryStringUrl);
        }
    }


    private void defineToolbar() {
        Log.d(TAG, "Setting toolbar");
        // TODO : 289) Defining toolbar
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.search_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

        // TODO : 298) Getting query from Search Manager
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            Log.d(TAG, "Search Option is working");
            String locationType = getIntent().getStringExtra(SearchManager.QUERY);

            String currentLocation = mContext.getSharedPreferences(
                    GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                    .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);

            Log.d(TAG, "Current Location : " + currentLocation);

            String locationQueryStringUrl = GoogleMapApi.BASE_URL + GoogleMapApi.NEARBY_SEARCH_TAG + "/" +
                    GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_TAG + "=" +
                    currentLocation + "&" + GoogleMapApi.RANK_BY_TAG + "=" + GoogleMapApi.DISTANCE_TAG +
                    "&" + GoogleMapApi.KEYWORD_TAG + "=" + locationType.replace(" ", "") + "&" +
                    GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY;
            Log.d(TAG, "Location Query Url : " + locationQueryStringUrl);

            getSearchResult(locationQueryStringUrl);
        }
    }

    // TODO : 291) Getting search results of location url
    private void getSearchResult(String locationQueryStringUrl) {
        Log.d(TAG, "getSearchResult is calling");

        // TODO : 292 ) Getting each attribute from JSON via Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, locationQueryStringUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray rootJsonArray = response.getJSONArray("results");
                    if (rootJsonArray.length() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        nosearchTextView.setVisibility(View.VISIBLE);
                        nosearchTextView.setText(getResources().getString(R.string.not_found));
                        Log.d(TAG, "placeHolder text : " + getResources().getString(R.string.not_found));
                    } else {
                        for (int i = 0; i < rootJsonArray.length(); i++) {
                            JSONObject singlePlaceJsonObject = (JSONObject) rootJsonArray.get(i);

                            // TODO : 293 ) Getting each attribute from JSON

                            String currentLocationId = singlePlaceJsonObject.getString("place_id");
                            String currentPlaceName = singlePlaceJsonObject.getString("name");

                            Double currentLocationLatitude = singlePlaceJsonObject
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");
                            Double currentLocationLongitude = singlePlaceJsonObject
                                    .getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            String currentLocationOpeningHourStatus = singlePlaceJsonObject
                                    .has("opening_hours") ? singlePlaceJsonObject
                                    .getJSONObject("opening_hours")
                                    .getString("open_now") : "Status Not Convenient";

                            Double currentLocationRating = singlePlaceJsonObject.has("rating") ?
                                    singlePlaceJsonObject.getDouble("rating") : 0;

                            String currentLocationAddress = singlePlaceJsonObject.has("vicinity") ?
                                    singlePlaceJsonObject.getString("vicinity") :
                                    "Address Not Available";

                            // TODO : 294 ) Adding each attribute to Location object

                            Location currentLocation = new Location();
                            currentLocation.setmLocationId(currentLocationId);
                            currentLocation.setmLocationName(currentPlaceName);
                            currentLocation.setmLocationLatitude(currentLocationLatitude);
                            currentLocation.setmLocationLongitude(currentLocationLongitude);
                            currentLocation.setmLocationOpeningHourStatus(currentLocationOpeningHourStatus);
                            currentLocation.setmLocationRating(currentLocationRating);
                            currentLocation.setmLocationAddress(currentLocationAddress);

                            // TODO : 295 ) Adding location object  to arraylist

                            mNearByLocationArrayList.add(currentLocation);
                        }

                        if(mNearByLocationArrayList.size() == 0){
                            recyclerView.setVisibility(View.GONE);
                            nosearchTextView.setVisibility(View.VISIBLE);
                            nosearchTextView.setText(getResources().getString(R.string.not_found));
                        }else {
                            recyclerView.setVisibility(View.VISIBLE);
                            nosearchTextView.setVisibility(View.GONE);

                            int orientation = GridLayout.VERTICAL;
                            int span = getResources().getInteger(R.integer.gridlayout_location_list_span);
                            boolean reverseLayout = false;
                            GridLayoutManager layoutManager = new GridLayoutManager(mContext, span, orientation, reverseLayout);
                            LocationItemListAdapter mLocationListAdapter = new LocationItemListAdapter(mContext, mNearByLocationArrayList, new LocationItemListAdapter.NearLocationListItemAdapterOnClickHandler() {
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
                            });
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mLocationListAdapter);

                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d(TAG, "JSON Error : " + error.getMessage());
            }
        });

        // TODO : 296 ) Adding request to volley
        AppController.getInstance().addToRequestQueue(request, getResources().getString(R.string.jsonArrayTag));




    }


}
