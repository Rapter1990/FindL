package com.germiyanoglu.android.findl.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;
import java.util.Date;

import butterknife.ButterKnife;

// TODO 10 ) Creating SplashScreenActivity to open this activity before Main Activity
// TODO : 197) Implementing GoogleApiClient as a connection and ResultCallback<LocationSettingsResult> as GPS with LocationListener to determine location to be updated
public class SplashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener {

    private static final String TAG = SplashScreenActivity.class.getName();

    private static final int SPLASH_SCREEN_TIMER = 1000;

    public static final int LOCATION_REQUEST_CODE = 1;
    public static final int LOCATION_PERMISSION_CODE = 2;

    // TODO : 198) Defining GoogleApiClient and LocationRequest
    private GoogleApiClient mGoogleApiClient; // Provides the entry point to Google Play services.
    private LocationRequest mCurrentLocationRequest;


    private FusedLocationProviderClient mFusedLocationClient;

    // TODO : 207) Providing access to the Location Settings API.
    private SettingsClient mSettingsClient;

    // TODO : 208) Storing parameters for requests to the FusedLocationProviderApi.
    private LocationRequest mLocationRequest;

    // TODO : 209) Storing the types of location services the client is interested in using. Used for checking
    // settings to determine if the device has optimal location settings.
    private LocationSettingsRequest mLocationSettingsRequest;

    // TODO : 210) Providing Callback for Location events.
    private LocationCallback mLocationCallback;

    // TODO : 211) Representing a geographical location.
    private Location location;

    // TODO : 204) Defining SharedPreferences to keep current location in SharedPreferences and String Value
    private SharedPreferences mLocationSharedPreferences;
    private String mCurrentLocation = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // TODO : 213) Calling createLocationCallback
        createLocationCallback();

        // TODO : 212) Calling buildGoogleApiClient
        buildGoogleApiClient();

        // TODO 18 ) Calling openMainActivity
        openMainActivity();


    }


    // TODO 17 ) Opening Main Activity after 1 seconds
    private void openMainActivity() {
        Log.d(TAG, "openMainActivity is working");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                //Start HomeScreenActivity
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                Log.d(TAG, "MainActivity is opening");

                //Stop SplashScreenActivity
                finish();
            }
        }, SPLASH_SCREEN_TIMER);

    }

    // TODO : 201) Creating Google Api
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    // TODO : 205) Connecting to the LocationServices API
    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mCurrentLocationRequest = LocationRequest.create();
        mCurrentLocationRequest.setInterval(10000);
        mCurrentLocationRequest.setFastestInterval(5000);
        mCurrentLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // TODO : 206 ) Determining location
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        // TODO : 214 ) --------------------------------------------------------------------



                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {





                    }

        });


    }

    // TODO : 202) While the connection is suspended, connect it again
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended is working");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed is working");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Log.d(TAG, "onResult is working");
    }

    // TODO : 199) Preparing Google Api by opening the app
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart is working");
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    // TODO : 200) Disconnecting Google Api by stoping the app
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop is working");
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    // TODO : 203) Gettting current location
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged is working");

        mCurrentLocation = String.valueOf(location.getLatitude()) + "," +
                String.valueOf(location.getLongitude());

        Log.d(TAG, "mCurrentLocation : " + location.getLatitude() + " " + location.getLongitude());

        SharedPreferences.Editor locationEditor = mLocationSharedPreferences.edit();
        locationEditor.putString(GoogleMapApi.CURRENT_LOCATION_DATA, mCurrentLocation); // -> connecting with 191th
        locationEditor.apply();

        Log.d(TAG, "onLocationChange");
    }

    // TODO : 213) Defining createLocationCallback to create a callback for receiving location events
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                location = locationResult.getLastLocation();
            }
        };
    }


}
