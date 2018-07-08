package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.modal.Location;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationSearchResultActivity extends AppCompatActivity {

    private static final String TAG = LocationSearchResultActivity.class.getName();

    private Context mContext;

    private ArrayList<Location> mNearByLocationArrayList = new ArrayList<>();

    // TODO : 287) Defining attributes of lcaotino_search_result xml file
    @BindView(R.id.location_search_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.location_search_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.location_search_list_empty_textview)
    TextView nosearchTextView;

    @BindView(R.id.location_search_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_result);
        Log.d(TAG, "onCreate is working");
        ButterKnife.bind(this);
        mContext = LocationSearchResultActivity.this;

        progressBar.setVisibility(View.VISIBLE);
        defineToolbar();


    }

    private void defineToolbar() {
        // TODO : 289) Defining toolbar
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.search_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO : 290) ----------------------------------------------------------------------------------------
}
