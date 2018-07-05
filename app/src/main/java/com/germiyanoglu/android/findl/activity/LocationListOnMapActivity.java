package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.AppController;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 147 ) Creating LocationListOnMapActivity to list location with its mark on Map
// TODO : 219 ) Implementing OnMapReadyCallback, GoogleMap.OnMarkerClickListener
public class LocationListOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private static final String TAG = LocationListOnMapActivity.class.getName();

    private Context mContext;

    // TODO : 220 ) Defining variables
    private ArrayList<Location> mNearByPlaceArrayList = new ArrayList<>();
    private GoogleMap mGoogleMap;
    private boolean mMapReady = false;
    private String mLocationTag;
    private String mLocationName;
    private String mLocationQueryStringUrl;
    private MapFragment mMapFragment;

    // TODO : 231 ) Defining attributes of activity_location_list_on_map
    @BindView(R.id.location_list_on_map_toolbar)
    Toolbar actionBar;

    @BindView(R.id.location_list_on_map_location_list_icon_view)
    ImageView tocImage;

    @BindView(R.id.location_list_on_map_location_list_placeholder_text_view)
    TextView placeHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list_on_map);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationListOnMapActivity.this;

        initializeVariable();
        locationURLDefination();
        defineToolbar();
        tocImageClick();
        placeHolderDefination();


    }

    private void placeHolderDefination() {
        // TODO : 235 ) Defining placeHolder TextView
        placeHolder.setText(mLocationName + " "+ getResources().getString(R.string.show_near_location));
        Log.d(TAG, "placeHolder text : " + mLocationName + " "+ getResources().getString(R.string.show_near_location));
    }

    private void tocImageClick() {
        // TODO : 232 ) Add clicable functionality for toc Image Icon
        tocImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationNearMeList = new Intent(mContext, LocationListActivity.class);
                locationNearMeList.putParcelableArrayListExtra(
                        GoogleMapApi.ALL_NEARBY_LOCATION, mNearByPlaceArrayList);
                locationNearMeList.putExtra(GoogleMapApi.LOCATION_TYPE_EXTRA_TEXT, mLocationTag);
                locationNearMeList.putExtra(GoogleMapApi.LOCATION_NAME_EXTRA_TEXT, mLocationName);
                startActivity(locationNearMeList);
                overridePendingTransition(R.anim.slide_next_right, R.anim.slide_next_left);
            }
        });
    }

    private void defineToolbar() {
        // TODO : 228 ) Defining Toolbar with its title
        setSupportActionBar(actionBar);
        String actionBarTitleText = getResources().getString(R.string.list_tag) + " "+ mLocationName;
        Log.d(TAG, "Toolbar text : " + actionBarTitleText);
        setTitle(actionBarTitleText);
        actionBar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        // TODO : 229 ) Providing toolbar with Back Arrow as return functionality
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void locationURLDefination() {
        // TODO : 226 ) Get current location via sharedPreference
        String currentLocation = mContext.getSharedPreferences(
                GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);

        Log.d(TAG, "Current Location : " + currentLocation);

        // TODO : 227 ) Determining Location Url
        mLocationQueryStringUrl = GoogleMapApi.BASE_URL + GoogleMapApi.NEARBY_SEARCH_TAG + "/" +
                GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_TAG + "=" +
                currentLocation + "&" + GoogleMapApi.RADIUS_TAG + "=" +
                GoogleMapApi.RADIUS_VALUE + "&" + GoogleMapApi.PLACE_TYPE_TAG + "=" + mLocationTag +
                "&" + GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY;

        Log.d(TAG, "Location URL: " + GoogleMapApi.BASE_URL + GoogleMapApi.NEARBY_SEARCH_TAG + "/" +
                GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_TAG + "=" +
                currentLocation + "&" + GoogleMapApi.RADIUS_TAG + "=" +
                GoogleMapApi.RADIUS_VALUE + "&" + GoogleMapApi.PLACE_TYPE_TAG + "=" + mLocationTag +
                "&" + GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY);
    }

    private void initializeVariable() {
        // TODO : 221 ) Defining Map Fragment as a reference
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        // TODO : 225 ) Getting lcoation tag and its name from MainActivity
        mLocationTag = getIntent().getStringExtra(GoogleMapApi.LOCATION_TYPE_EXTRA_TEXT);
        mLocationName = getIntent().getStringExtra(GoogleMapApi.LOCATION_NAME_EXTRA_TEXT);

        Log.d(TAG, "Location Tag : " + mLocationTag + " / Location Name : " + mLocationName);
    }

    // TODO : 230 ) Returning back from Toolbar
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
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mMapReady = true;

        // TODO : 236 ) Define a method to locate location near me via JSON
        getLocationNearMe(mLocationQueryStringUrl);

    }


    private void getLocationNearMe(String mLocationQueryStringUrl) {

        // TODO : 237 ) Getting each attribute from JSON via Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mLocationQueryStringUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray rootJsonArray = response.getJSONArray("results");
                    if (rootJsonArray.length() == 0){
                        placeHolder.setText(mLocationName + " "+ getResources().getString(R.string.not_found));
                        Log.d(TAG, "placeHolder text : " + mLocationName + " "+ getResources().getString(R.string.not_found));
                    }else{
                        for(int i=0;i<rootJsonArray.length();i++){
                            JSONObject singlePlaceJsonObject = (JSONObject) rootJsonArray.get(i);

                            // TODO : 238 ) Getting each attribute from JSON

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

                            // TODO : 239 ) Adding each attribute to Location object

                            Location currentLocation = new Location();
                            currentLocation.setmLocationId(currentLocationId);
                            currentLocation.setmLocationName(currentPlaceName);
                            currentLocation.setmLocationLatitude(currentLocationLatitude);
                            currentLocation.setmLocationLongitude(currentLocationLongitude);
                            currentLocation.setmLocationOpeningHourStatus(currentLocationOpeningHourStatus);
                            currentLocation.setmLocationRating(currentLocationRating);
                            currentLocation.setmLocationAddress(currentLocationAddress);

                            // TODO : 240 ) Adding location object  to arraylist

                            mNearByPlaceArrayList.add(currentLocation);
                        }

                        // TODO : 240 ) Showing each location on the map
                        if(mMapReady){

                            // TODO : 241) Defining current location
                            String currentLocationString = getSharedPreferences(
                                    GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                                    .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);
                            String currentPlace[] = currentLocationString.split(",");

                            Log.d(TAG, "currentLocationString : " + currentPlace[0] + " " + currentPlace[1]);

                            // TODO : 242) Defining Camera Position
                            CameraPosition cameraPosition = CameraPosition.builder()
                                    .target(new LatLng(Double.valueOf(currentPlace[0])
                                            , Double.valueOf(currentPlace[1])))
                                    .zoom(13)
                                    .bearing(0)
                                    .tilt(0)
                                    .build();

                            Log.d(TAG, "cameraPosition is defined ");

                            // TODO : 243) Defining camera position to google map
                            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            // TODO : 244) Setting lcaotion in arraylist on the map
                            for (int i = 0; i < mNearByPlaceArrayList.size(); i++) {
                                Double currentLatitude = mNearByPlaceArrayList.get(i).getmLocationLatitude();
                                Double currentLongitude = mNearByPlaceArrayList.get(i).getmLocationLongitude();
                                LatLng currentLatLng = new LatLng(currentLatitude, currentLongitude);
                                mGoogleMap.addMarker(new MarkerOptions()
                                        .position(currentLatLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_map)));
                            }

                            // TODO : 245) Adding current user position
                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position((new LatLng(Double
                                            .valueOf(currentPlace[0]), Double.valueOf(currentPlace[1]))))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_user_location_on_map)));

                        }

                    }


                }catch (Exception e){
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

        // TODO : 246) Adding request to volley
        AppController.getInstance().addToRequestQueue(request, getResources().getString(R.string.jsonArrayTag));

    }

}
