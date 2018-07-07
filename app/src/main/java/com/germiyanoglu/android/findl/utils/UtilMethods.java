package com.germiyanoglu.android.findl.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

// TODO : 102 ) Creating UtilMethods to define some method used for project
public class UtilMethods {

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
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(secondPointLatitude - firstPointLatitude);
        double dLon = Math.toRadians(secondPointLongitude - firstPointLatitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(firstPointLatitude))
                * Math.cos(Math.toRadians(secondPointLatitude)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        double meter = valueResult % 1000;

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#.##", symbols);
        double kmInDec = 0;
        double meterInDec = 0;
        try {
            kmInDec = (double) format.parse(format.format(km));
            meterInDec = (double) format.parse(format.format(meter));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.d("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        double total = kmInDec +  meterInDec;

        double rounded = Math.round(total * 1e2) / 1e2;

        return rounded;
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
