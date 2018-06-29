package com.germiyanoglu.android.findl.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.germiyanoglu.android.findl.R;

import butterknife.ButterKnife;

// TODO 10 ) Creating SplashScreenActivity to open this activity before Main Activity
public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getName();

    private static final int SPLASH_SCREEN_TIMER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);

        // TODO 18 ) Calling openMainActivity
        openMainActivity();

    }


    // TODO 17 ) Opening Main Activity after 1 seconds
    private void openMainActivity(){
        Log.d(TAG,"openMainActivity is working");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                //Start HomeScreenActivity
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                Log.d(TAG,"MainActivity is opening");

                //Stop SplashScreenActivity
                finish();
            }
        }, SPLASH_SCREEN_TIMER);

    }
}
