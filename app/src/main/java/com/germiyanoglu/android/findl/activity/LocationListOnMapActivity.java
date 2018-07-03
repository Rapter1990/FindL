package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;

import butterknife.ButterKnife;

// TODO : 147 ) Creating LocationListOnMapActivity to list location with its mark on Map
public class LocationListOnMapActivity extends AppCompatActivity {

    private static final String TAG = LocationListOnMapActivity.class.getName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list_on_map);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationListOnMapActivity.this;
    }




    /*
    *  1 - Splash Activity permission
    *  2 - About Detail and Favorite Detail
    *
    * */


}
