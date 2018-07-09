package com.germiyanoglu.android.findl.fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.data.LocationDetailContract;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.germiyanoglu.android.findl.utils.UtilMethods.getPhoneCallPermission;

// TODO : 155 ) Defining LocationInformationAbout class to show location information and its exact location on the map
public class LocationInformationAbout extends Fragment implements OnMapReadyCallback {

    private static final String TAG = LocationInformationAbout.class.getName();


    // TODO : 156 ) Defining attributes of LocationInformationAbout

    @BindView(R.id.location_information_phone_icon)
    ImageView mPhoneCallIcon;

    @BindView(R.id.location_information_website_icon)
    ImageView mWebsiteIcon;

    @BindView(R.id.location_information_favourite_icon)
    ImageView mFavoriteIcon;

    @BindView(R.id.location_address_text_view)
    TextView mLocationAddressTextView;

    @BindView(R.id.location_phone_number_text_view)
    TextView mLocationPhoneTextView;

    @BindView(R.id.location_website_text_view)
    TextView mLocationWebsiteTextView;

    @BindView(R.id.location_status_text_view)
    TextView mLocationOpeningStatusTextView;

    MapFragment mapFragment;

    // TODO : 160 ) Defining variables
    private Location mLocation;

    private static final int PERMISSION_REQUEST_CODE = 1;

    // TODO : 189) Defining Google Map variables
    private GoogleMap mGoogleMap;
    private boolean mMapReady = false;

    // TODO : 157 ) Creating bundle with creating LocationInformationAbout
    public LocationInformationAbout() {
        super();
        Log.d(TAG, "LocationInformationAbout Bundle is working");
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView Bundle is working");

        // TODO : 158 ) Defining view
        Context context = container.getContext();
        int layoutIdForListItem = R.layout.fragment_location_information;
        inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, container, shouldAttachToParentImmediately);
        ButterKnife.bind(this, view);

        // TODO : 161 ) Defining Map Fragment
        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.location_detail_map);
        mapFragment.getMapAsync(this);

        // TODO : 167 ) Getting lcoation from Bundle
        mLocation = getLocationFromBundle();

        // TODO : 168 ) Displaying location Information
        displayLocationInformation(mLocation);

        // TODO : 165 ) Calling callPhone,openWebsite,addorDeleteFavorite
        callPhone(mLocation);
        openWebsite(mLocation);
        addorDeleteFavorite(mLocation);


        // TODO : 188) Checking whether selected location is in the favorites or not.
        if(checkFavoriteIconInDatabase(mLocation.getmLocationId())) {
            Log.d(TAG, "White fill favorite icon ");
            Picasso.get().load(R.drawable.ic_favorite_white_fill)
                    .into(mFavoriteIcon);
        }else{
            Log.d(TAG, "White border favorite icon ");
            Picasso.get().load(R.drawable.ic_favorite_white_border)
                    .into(mFavoriteIcon);
        }



        return view;
    }

    // TODO : 159 ) Defining getLocationFromBundle class to get location from previous activity
    private Location getLocationFromBundle() {
        Log.d(TAG, "getLocationFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable(GoogleMapApi.CURRENT_LOCATION_DATA);
        } else {
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady is working");

        // TODO : 190) Assigning new variable to Google Map variables
        mMapReady = true;
        mGoogleMap = googleMap;


        // TODO : 191) Defining current location
        String currentLocationString = getContext().getSharedPreferences(
                GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);
        String currentPlace[] = currentLocationString.split(",");

        Log.d(TAG, "currentLocationString : " + currentPlace[0] + " " + currentPlace[1]);

        // TODO : 192) Defining Camera Position
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(Double.valueOf(currentPlace[0])
                        , Double.valueOf(currentPlace[1])))
                .zoom(12.2f)
                .bearing(0)
                .tilt(0)
                .build();

        Log.d(TAG, "cameraPosition is defined ");

        // TODO : 193) Defining camera position to google map
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Log.d(TAG, "cameraPosition is added to Google Map ");

        // TODO : 194) Adding current position of user to Google Map
        mGoogleMap.addMarker(new MarkerOptions()
                .position((new LatLng(Double
                        .valueOf(currentPlace[0]), Double.valueOf(currentPlace[1]))))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_user_location_on_map)));

        Log.d(TAG, "Current user position is added to Google Map ");

        // TODO : 195) Adding selected location position
        mGoogleMap.addMarker(new MarkerOptions()
                .position((new LatLng(
                        mLocation.getmLocationLatitude(), mLocation.getmLocationLongitude())))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_map)));

        Log.d(TAG, "Selected location is added to Google Map ");

        // TODO : 196) Draw a line between two locations
        PolylineOptions drawLineBetweenTwoLocation = new PolylineOptions();
        drawLineBetweenTwoLocation.add(new LatLng(Double.valueOf(currentPlace[0])
                , Double.valueOf(currentPlace[1])))
                .add(new LatLng(mLocation.getmLocationLatitude(), mLocation.getmLocationLongitude()))
                .width(5).color(Color.BLUE).geodesic(true);

        mGoogleMap.addPolyline(drawLineBetweenTwoLocation);

        Log.d(TAG, "Draw a line between two locations");

    }


    // TODO : 162 ) Define callPhone method to call phone number of location
    private void callPhone(final Location mLocation) {
        mPhoneCallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mPhoneCallIcon is clicked");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // TODO : 169 ) Checking whether permission is given for calling or not
                    if (getActivity().checkSelfPermission(UtilMethods.getPhoneCallPermission())
                            != PackageManager.PERMISSION_GRANTED) {

                        // TODO : 175 ) Calling shouldShowRequestPermissionRationale to see if the user checked Never ask again or not.
                        if (shouldShowRequestPermissionRationale(
                                UtilMethods.getPhoneCallPermission())) {

                            // TODO : 178 ) Openining AlertDialog to give a permission
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(R.string.permission_title)
                                    .setMessage(R.string.call_permission_message)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).show();
                        }else{
                            // TODO : 170 ) Requestng to give a permission for calling
                            requestPermissions(new String[]{UtilMethods.getPhoneCallPermission()},
                                    PERMISSION_REQUEST_CODE);
                        }

                    } else {
                        // TODO : 176 ) Calling Location Phone Number
                        callPhoneNumber(LocationInformationAbout.this.mLocation);
                    }

                } else {
                    // TODO : 171 ) Calling Location Phone Number
                    callPhoneNumber(LocationInformationAbout.this.mLocation);
                }

            }
        });
    }

    // TODO : 163 ) Define openWebsite method to go to website of location
    private void openWebsite(final Location location) {
        mWebsiteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mWebsiteIcon is clicked");
                // TODO : 177 ) Checking wheter website of location is valid or not.
                if (location.getmLocationWebsite().startsWith("h", 0)) {
                    Log.d(TAG, "Website is valid");
                    Intent websiteUrlIntent = new Intent(Intent.ACTION_VIEW);
                    websiteUrlIntent.setData(Uri.parse(location.getmLocationWebsite()));
                    getActivity().startActivity(websiteUrlIntent);

                } else {
                    Log.d(TAG, "No Website");
                    Toast.makeText(getActivity(), R.string.no_website,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO : 164 ) Define addorDeleteFavorite method to add or remove location in favorite
    private void addorDeleteFavorite(final Location mLocation) {
        mFavoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mFavoriteIcon is clicked");

                final Bitmap FavoriteImageviewBitmap = ((BitmapDrawable)mFavoriteIcon.getDrawable()).getBitmap();
                Drawable myDrawable = getResources().getDrawable(R.drawable.ic_favorite_white_border);
                final Bitmap favoriteWhiteBitmap = ((BitmapDrawable) myDrawable).getBitmap();

                Log.d(TAG,"Favorite Icon : " + mFavoriteIcon.getDrawable().getConstantState());
                Log.d(TAG,"Comparison Icon : " + ContextCompat.getDrawable(getActivity(),
                        R.drawable.ic_favorite_white_border).getConstantState());


                /*mFavoriteIcon.getDrawable().getConstantState().equals(
                        ContextCompat.getDrawable(getActivity(),
                                R.drawable.ic_favorite_white_border).getConstantState())*/

                // TODO : 179 ) Checking whether favorite icon is empty icon or not
                if(FavoriteImageviewBitmap.sameAs(favoriteWhiteBitmap)){
                    Log.d(TAG, "Converting Favorite White Border to Favorite Fill White");
                    // TODO : 180 ) Defining addLocation method to add it into database
                    addLocation(mLocation);

                    Picasso.get().load(R.drawable.ic_favorite_white_fill)
                            .into(mFavoriteIcon);

                    // TODO : 182 ) Showing a Toast Message
                    Toast.makeText(getActivity(), R.string.add_location_favorite,
                            Toast.LENGTH_SHORT).show();

                }else{
                    Log.d(TAG, "Converting Favorite Fill White to Favorite White Border");
                    // TODO : 183) Deleting the location from the database.
                    String where = LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID + "=?";
                    String[] whereArgs = new String[]{String.valueOf(mLocation.getmLocationId())};
                    getContext().getContentResolver().delete(LocationDetailContract.LocationDetailEntry.CONTENT_URI, where, whereArgs);

                    Picasso.get().load(R.drawable.ic_favorite_white_border)
                            .into(mFavoriteIcon);

                    Toast.makeText(getActivity(), getString(R.string.delete_location_favorite), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO : 166 ) Dispalying location information
    private void displayLocationInformation(final Location location) {
        Log.d(TAG, "displayLocationInformation is working");
        if (location != null) {
            mLocationAddressTextView.setText(location.getmLocationAddress());
            mLocationPhoneTextView.setText(location.getmLocationPhoneNumber());
            mLocationWebsiteTextView.setText(location.getmLocationWebsite());
            mLocationOpeningStatusTextView.setText(location.getmLocationOpeningHourStatus());
        }

    }

    // TODO : 172 ) Defining onRequestPermissionsResult to activate call number via permission code
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callPhoneNumber(LocationInformationAbout.this.mLocation);
        }
    }

    // TODO : 173 ) Defining callPhoneNumber method to call phone number of location
    private void callPhoneNumber(Location mLocation) {
        Log.d(TAG, "callPhoneNumber is working");
        if (mLocation.getmLocationPhoneNumber().startsWith("+", 0)) {
            Log.d(TAG, "Number is defined");
            Intent callPhoneIntent = new Intent(Intent.ACTION_CALL);
            callPhoneIntent.setData(Uri.parse("tel:" + mLocation.getmLocationPhoneNumber()));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d(TAG, "No calling permission");
                return;
            }
            Log.d(TAG, "Calling Number");
            getContext().startActivity(callPhoneIntent);
        } else {
            Log.d(TAG, "No Number");
            Toast.makeText(getActivity(), R.string.no_phone_number,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // TODO : 181 ) Defining addLocation method
    private void addLocation(Location mLocation){

        ContentValues contentValues = new ContentValues();

        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID,mLocation.getmLocationId());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LATITUDE,mLocation.getmLocationLatitude());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LONGITUDE,mLocation.getmLocationLongitude());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME,mLocation.getmLocationName());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS,mLocation.getmLocationOpeningHourStatus());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING,mLocation.getmLocationRating());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ADDRESS,mLocation.getmLocationAddress());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_PHONE_NUMBER,mLocation.getmLocationPhoneNumber());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_WEBSITE,mLocation.getmLocationWebsite());
        contentValues.put(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_SHARE_LINK,mLocation.getmLocationShareLink());

        getContext().getContentResolver().insert(LocationDetailContract.LocationDetailEntry.CONTENT_URI,
                contentValues);


    }

    // TODO : 184) Defining checkFavoriteIconInDatabase to check whether location was stored in the database before or not
    private boolean checkFavoriteIconInDatabase(final String id){

        boolean ischecked = false;

        ArrayList<String> locationIdList = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Cursor locationCursor = getContext().getContentResolver()
                    .query(LocationDetailContract.LocationDetailEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null,
                    null);

            if(locationCursor != null && locationCursor.getCount()>0){
                // TODO : 185) Moving cursor to first variable
                locationCursor.moveToFirst();

                // TODO : 186) Getting id of location form table in database and add it into location object
                while(!locationCursor.isAfterLast()){

                    int locationColumnIndexOfId = locationCursor.getColumnIndex
                            (LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID);

                    String locationId =
                            locationCursor.getString(locationColumnIndexOfId);

                    locationIdList.add(locationId);

                    locationCursor.moveToNext();
                }

                // TODO : 186) Checking whether id from location id and id from location in database are the same or not
                if (locationIdList.size() != 0) {
                    for (int i = 0; i < locationIdList.size(); i++) {
                        if (locationIdList.get(i).equals(id))
                            ischecked = true;
                    }
                }

                // TODO : 187) Closing cursor
                locationCursor.close();

                return ischecked;
            }

        }

        return ischecked;
    }


}
