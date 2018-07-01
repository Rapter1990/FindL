package com.germiyanoglu.android.findl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.bottomnavigationmenu.BottomNavigationBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = InfoActivity.class.getName();

    private static final int ICON_NUMBER_MENU = 3;

    // TODO : 91 ) Defining attributes of Info Activity
    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx mBottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);

        // TODO 95 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();
    }

    // TODO 92 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG,"bottomNavigationViewMenu is starting");
        BottomNavigationBar.setBottomBottomNavigationBar(mBottomNavigationViewEx);

        // TODO 93 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBar.navitageIcon(this,this,mBottomNavigationViewEx);

        // TODO 94 ) Showing Icon List
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }
}
