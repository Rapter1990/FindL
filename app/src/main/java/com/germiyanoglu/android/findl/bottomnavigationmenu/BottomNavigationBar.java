package com.germiyanoglu.android.findl.bottomnavigationmenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.activity.FavoriteLocationActivity;
import com.germiyanoglu.android.findl.activity.InfoActivity;
import com.germiyanoglu.android.findl.activity.MainActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

// TODO 21 ) Defining Bottom Navigation bar to design and show it in the bottom
public class BottomNavigationBar {

    private static final String TAG = BottomNavigationBar.class.getName();

    // TODO 22 ) Defining setBottomNavigationBarAnimation to revoke animation of BottomNavigationBar
    public static void setBottomBottomNavigationBar(BottomNavigationViewEx bottomNavigationBarAnimation){
        Log.d(TAG,"setBottomBottomNavigationBar is working");
        bottomNavigationBarAnimation.enableAnimation(false);
        bottomNavigationBarAnimation.enableItemShiftingMode(false);
        bottomNavigationBarAnimation.enableShiftingMode(false);
    }

    // TODO 23 ) Defining navitageIcon to navigation between icons
    public static void navitageIcon(final Context context, final Activity activityAnimation, final BottomNavigationViewEx bottomNavigationBarAnimation){

        bottomNavigationBarAnimation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.d(TAG,"setOnNavigationItemSelectedListener is working");

                switch (item.getItemId()) {
                    case R.id.ic_favorite:
                        Log.d(TAG,"Favorite Activity  is working");
                        openFavoriteActivity(context);  // ICON_NUMBER_MENU = 1;
                        activityAnimation.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;
                    case R.id.ic_contract:
                        Log.d(TAG,"Contract Activity is working");
                        openContractActivity(context);  // ICON_NUMBER_MENU = 2;
                        activityAnimation.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;
                    case R.id.ic_info:
                        Log.d(TAG,"Info Activity is working");
                        openInfoActivity(context);  // ICON_NUMBER_MENU = 3;
                        activityAnimation.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;
                    case R.id.ic_exit:
                        Log.d(TAG,"Exit is working");
                        exitApplication(context); // ICON_NUMBER_MENU = 4;
                        activityAnimation.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;
                    case R.id.ic_homepage:
                        openHomeActivity(context); // ICON_NUMBER_MENU = 0;
                        activityAnimation.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        break;

                    default:
                        return false;
                }

                return false;
            }
        });

    }


    // TODO 26 ) Opening openFavoriteActivity
    private static void openFavoriteActivity(final Context context){
        Log.d(TAG,"openFavoriteActivity is working");
        /*Intent intent = new Intent(context, FavoriteLocationActivity.class);
        context.startActivity(intent);*/
    }

    // TODO 27 ) Opening openContractActivity
    private static void openContractActivity(final Context context){
        Log.d(TAG,"openContractActivity is working");
        /*Intent intent = new Intent(context, activity.class);
        context.startActivity(intent);*/
    }

    // TODO 28 ) Opening openInfoActivity
    private static void openInfoActivity(final Context context){
        Log.d(TAG,"openInfoActivity is working");
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }

    // TODO 29 ) Opening exitApplication
    private static void exitApplication(final Context context){
        Log.d(TAG,"openSearchActivity is working");

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(homeIntent);

        /*android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);*/
    }

    // TODO 37 ) Opening openHomeActivity
    private static void openHomeActivity(final Context context){
        Log.d(TAG,"openHomeActivity is working");
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
