package com.germiyanoglu.android.findl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.bottomnavigationmenu.BottomNavigationBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 0;

    // TODO 30 ) Defining attributes of Main Activity
    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx mBottomNavigationViewEx;

    @BindView(R.id.main_screen_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_screen_cardview)
    CardView mCardView;

    @BindView(R.id.main_screen_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.main_screen_progress_bar)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);

        // TODO 35 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();
    }

    // TODO 34 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG,"bottomNavigationViewMenu is starting");
        BottomNavigationBar.setBottomBottomNavigationBar(mBottomNavigationViewEx);

        // TODO 35 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBar.navitageIcon(this,this,mBottomNavigationViewEx);

        // TODO 36 ) Showing Icon List
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }
}
