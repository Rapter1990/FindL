package com.germiyanoglu.android.findl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;

import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = InfoActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);
    }
}
