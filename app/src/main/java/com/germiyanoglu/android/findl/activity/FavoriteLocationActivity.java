package com.germiyanoglu.android.findl.activity;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.FavoriteLocationListItemAdapter;
import com.germiyanoglu.android.findl.data.LocationDetailContract;
import com.germiyanoglu.android.findl.modal.Location;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 277) Creating FavoriteLocationActivity to list favorite location
public class FavoriteLocationActivity extends AppCompatActivity {

    private static final String TAG = FavoriteLocationActivity.class.getName();

    private Context mContext;

    // TODO : 278) Defining attributes from activity_favorite_location
    @BindView(R.id.favorite_location_list_toolbar)
    Toolbar toolbar;

    @BindView(R.id.favorite_location_list_recycler_view)
    RecyclerView favoriteLocationRecyleView;

    @BindView(R.id.favorite_location_list_empty_textview)
    TextView favoriteEmptyTextView;

    // TODO : 281) Defining variables
    private ArrayList<Location> mFavouriteLocationArrayList = new ArrayList<>();
    public static final int FAVOURITE_LOCATION_DETAIL_LOADER = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_location);
        ButterKnife.bind(this);
        mContext = FavoriteLocationActivity.this;
        defineToolbar();
        defineLayout();

        // TODO : 284) Initialize loader Manager
        getLoaderManager().initLoader(FAVOURITE_LOCATION_DETAIL_LOADER, null,
                (android.app.LoaderManager.LoaderCallbacks<Object>) favoriteLocationLoaderListener);
    }


    private void defineToolbar() {
        // TODO : 279) Defining Toolbar
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.favourite_location_list));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void defineLayout() {
        // TODO : 282) Defining layout for listing favorite location
        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.gridlayout_location_list_span);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, span, orientation, reverseLayout);
        FavoriteLocationListItemAdapter mFavoriteLocationListAdapter = new FavoriteLocationListItemAdapter(mContext, null);
        favoriteLocationRecyleView.setLayoutManager(layoutManager);
        favoriteLocationRecyleView.setAdapter(mFavoriteLocationListAdapter);
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

    // TODO : 283) Defining Loader Manager Method to load favorite location from database
    LoaderManager.LoaderCallbacks<Cursor> favoriteLocationLoaderListener = new LoaderManager.LoaderCallbacks<Cursor>() {


        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            //define projection for the favouritePlace Details (No of Column)
            String[] projection = {
                    LocationDetailContract.LocationDetailEntry._ID,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LATITUDE,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LONGITUDE,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ADDRESS,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_PHONE_NUMBER,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_WEBSITE,
                    LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_SHARE_LINK
            };

            return new CursorLoader(mContext,
                    LocationDetailContract.LocationDetailEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            ((FavoriteLocationListItemAdapter) favoriteLocationRecyleView.getAdapter()).swapCursor(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            ((FavoriteLocationListItemAdapter) favoriteLocationRecyleView.getAdapter()).swapCursor(null);
        }
    };


    // TODO : 285) -------------------------------------------------------------------------
}
