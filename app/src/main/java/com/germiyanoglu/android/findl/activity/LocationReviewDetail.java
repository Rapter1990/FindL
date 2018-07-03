package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;

import butterknife.ButterKnife;

public class LocationReviewDetail extends AppCompatActivity {

    private static final String TAG = LocationListOnMapActivity.class.getName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_review_detail);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationReviewDetail.this;
    }
}
