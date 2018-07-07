package com.germiyanoglu.android.findl.utils;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

// TODO : 102 ) Creating UtilMethods to define some method used for project
public class UtilMethods {

    private static final String TAG = UtilMethods.class.getName();

    // TODO : 103 ) Creating isNetworkAvailable for providing whether Internet connection is convenient or not
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    // TODO : 104 ) Creating CalculationByDistance to calculate distance between two location
    public static double CalculationByDistance(Double firstPointLatitude,
                                        Double firstPointLongitude,
                                        Double secondPointLatitude,
                                        Double secondPointLongitude) {

        Location locationA = new Location("point A");

        locationA.setLatitude(firstPointLatitude);
        locationA.setLongitude(firstPointLongitude);

        Location locationB = new Location("point B");

        locationB.setLatitude(secondPointLatitude);
        locationB.setLongitude(secondPointLongitude);

        float distance = locationA.distanceTo(locationB);
        Log.d(TAG,"Distance : " + distance);

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#.##", symbols);

        double totalDistance = 0.0;
        try {
            totalDistance = (double) format.parse(format.format(distance));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"Total Distance : " + totalDistance);
        return distance;

    }

    // TODO : 131 ) Define a method for giving a permission when it is asked
    public static String getPhoneCallPermission(){
        return Manifest.permission.CALL_PHONE;
    }

    public static String getAccessFineLocationPermission(){
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    public static String getAccessCoarseLocationPermission(){
        return Manifest.permission.ACCESS_COARSE_LOCATION;
    }

    public static String getAcessNetworkStatePermission(){
        return Manifest.permission.ACCESS_NETWORK_STATE;
    }

    public static String getInternetPermission(){
        return Manifest.permission.INTERNET;
    }



}
