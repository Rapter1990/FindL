package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.ViewPagerAdapter;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.modal.UserLocationRating;
import com.germiyanoglu.android.findl.utils.AppController;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 258 ) Creating LocationDetailActivity class to show detail Information about location
public class LocationDetailActivity extends AppCompatActivity {


    private static final String TAG = LocationDetailActivity.class.getName();

    private Context mContext;

    // TODO : 261 ) Defining attributes of activity_location_detail.xml
    @BindView(R.id.location_detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.location_detail_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.location_detail_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.location_detail_viewpager)
    ViewPager viewPager;

    private ArrayList<UserLocationRating> mPlaceUserRatingsArrayList = new ArrayList<>();
    private String mCurrentLocationDetailUrl = "";
    private String mLocationShareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        ButterKnife.bind(this);
        mContext = LocationDetailActivity.this;
        // TODO : 269 ) Setting VISIBLE to progressBar
        progressBar.setVisibility(View.VISIBLE);


        defineToolbar();
        defineLocationUrl();

        // TODO 270 ) Define getAllLocationInformationFromJSON
        getAllLocationInformationFromJSON(mCurrentLocationDetailUrl);


    }


    private void defineLocationUrl() {
        // TODO : 268 ) Defining LocationUrl by using lcoation id from LocationListActivity
        String currentPlaceDetailId = getIntent().getStringExtra(GoogleMapApi.LOCATION_ID_EXTRA_TEXT);
        mCurrentLocationDetailUrl = GoogleMapApi.BASE_URL + GoogleMapApi.LOCATION_DETAIL_TAG + "/" +
                GoogleMapApi.OUTPUT_FORMAT_TAG + "?" + GoogleMapApi.LOCATION_PLACE_ID_TAG + "=" +
                currentPlaceDetailId + "&" + GoogleMapApi.API_KEY_TAG + "=" + GoogleMapApi.API_KEY;
        Log.d(TAG, "Location URL: " + mCurrentLocationDetailUrl);
    }

    private void defineToolbar() {
        // TODO : 267 ) Defining toolbar
        setSupportActionBar(toolbar);
        setTitle(R.string.location_detail_information);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO : 266 ) Adding Menu in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // TODO 271 ) Defining share icon to share location
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.ic_share:
                Intent sharePlaceUrlIntent = new Intent(Intent.ACTION_SEND);
                sharePlaceUrlIntent.setType("text/plain");
                sharePlaceUrlIntent.putExtra(Intent.EXTRA_TEXT, mLocationShareUrl);
                startActivity(sharePlaceUrlIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO 272 )  Getting all location information from JSON
    private void getAllLocationInformationFromJSON(String mCurrentPlaceDetailUrl) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mCurrentPlaceDetailUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                progressBar.setVisibility(View.GONE);
                try{

                    JSONObject rootJsonObject = response.getJSONObject("result");

                    String currentLocationId = rootJsonObject.getString("place_id");

                    String currentLocationName = rootJsonObject.getString("name");

                    Double currentLocationLatitude = rootJsonObject
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");

                    Double currentLocationLongitude = rootJsonObject
                            .getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");

                    String currentLocationOpeningHourStatus = rootJsonObject
                            .has("opening_hours") ? rootJsonObject.getJSONObject("opening_hours")
                            .getString("open_now") : "Status Not Available";
                    if (currentLocationOpeningHourStatus.equals("true"))
                        currentLocationOpeningHourStatus = getString(R.string.open_now);
                    else if (currentLocationOpeningHourStatus.equals("false"))
                        currentLocationOpeningHourStatus = getString(R.string.closed);

                    Double currentLocationRating = rootJsonObject.has("rating") ?
                            rootJsonObject.getDouble("rating") : 0;

                    String currentLocationAddress = rootJsonObject.has("formatted_address") ?
                            rootJsonObject.getString("formatted_address") :
                            "Address Not Available";

                    String currentLocationPhoneNumber = rootJsonObject
                            .has("international_phone_number") ? rootJsonObject
                            .getString("international_phone_number") :
                            "Not defined Phone Number";

                    String currentLocationWebsite = rootJsonObject.has("website") ?
                            rootJsonObject.getString("website") :
                            "Not defined Website";

                    String currentLocationShareLink = rootJsonObject.getString("url");
                    mLocationShareUrl = currentLocationShareLink;

                    Location currentlocation = new Location();
                    currentlocation.setmLocationId(currentLocationId);
                    currentlocation.setmLocationName(currentLocationName);
                    currentlocation.setmLocationLatitude(currentLocationLatitude);
                    currentlocation.setmLocationLongitude(currentLocationLongitude);
                    currentlocation.setmLocationOpeningHourStatus(currentLocationOpeningHourStatus);
                    currentlocation.setmLocationRating(currentLocationRating);
                    currentlocation.setmLocationAddress(currentLocationAddress);
                    currentlocation.setmLocationPhoneNumber(currentLocationPhoneNumber);
                    currentlocation.setmLocationWebsite(currentLocationWebsite);
                    currentlocation.setmLocationShareLink(currentLocationShareLink);

                    if (rootJsonObject.has("reviews")) {

                        JSONArray reviewJsonArray = rootJsonObject.getJSONArray("reviews");
                        for (int i = 0; i < reviewJsonArray.length(); i++) {
                            JSONObject currentUserRating = (JSONObject) reviewJsonArray.
                                    get(i);

                            String userName = currentUserRating.getString("author_name");
                            String userProfilePhoto = currentUserRating.getString("profile_photo_url");
                            Double userRating = currentUserRating.getDouble("rating");
                            String userDescriptionTime = currentUserRating.getString("relative_time_description");
                            String userDescription = currentUserRating.getString("text");

                            UserLocationRating currentLocationUserRating = new UserLocationRating();
                            currentLocationUserRating.setmUserName(userName);
                            currentLocationUserRating.setmUserProfilePictureUrl(userProfilePhoto);
                            currentLocationUserRating.setmUserPlaceRating(userRating);
                            currentLocationUserRating.setmUserRatingDateTime(userDescriptionTime);
                            currentLocationUserRating.setmAuthorReviewText(userDescription);

                            mPlaceUserRatingsArrayList.add(currentLocationUserRating);
                        }
                    }

                    // TODO 273 )  Defining bundle from location information and review
                    Bundle currentLocationDetailData = new Bundle();
                    currentLocationDetailData.putParcelable(
                            GoogleMapApi.CURRENT_LOCATION_DATA, currentlocation);

                    Bundle currentLocationUserRatingDetail = new Bundle();
                    currentLocationUserRatingDetail.putParcelableArrayList(
                            GoogleMapApi.CURRENT_LOCATION_USER_RATING,
                            mPlaceUserRatingsArrayList);


                    // TODO 274 )  Setting these bundle in the viewpager
                    ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(
                            getSupportFragmentManager(),mContext);
                    viewpagerAdapter.setBundleData(currentLocationDetailData, currentLocationUserRatingDetail);
                    viewPager.setAdapter(viewpagerAdapter);

                    // TODO 275 )  Setting viewpager to tablayout
                    tabLayout.setupWithViewPager(viewPager);


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

        // TODO : 276) Adding request to volley
        AppController.getInstance().addToRequestQueue(request, getResources().getString(R.string.jsonArrayTag));

    }
}
