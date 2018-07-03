package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;

import butterknife.ButterKnife;

public class LocationSearchResultActivity extends AppCompatActivity {

    private static final String TAG = LocationSearchResultActivity.class.getName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_result);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationSearchResultActivity.this;
    }
}
