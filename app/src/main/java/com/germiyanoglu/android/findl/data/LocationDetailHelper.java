package com.germiyanoglu.android.findl.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO : 47 ) Creating LocationDetailHelper for creating and updating database
public class LocationDetailHelper extends SQLiteOpenHelper {

    public static final String TAG = LocationDetailHelper.class.getSimpleName();

    // TODO : 48 ) Defining database name and its version
    private static final String DATABASE_NAME       = "locationlist.db";
    private static final int    DATABASE_VERSION    = 1;

    // TODO : 49 ) Defining a constuctor with database name and its version
    public LocationDetailHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO : 50 ) Creating a String variable for SQL Query to create a table in the database.
        final String CREATE_MOVIELIST_TABLE =
                "CREATE TABLE " + LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME + "(" +
                        LocationDetailContract.LocationDetailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID + " INTEGER NOT NULL," +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LATITUDE + " REAL, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_LONGITUDE + " REAL, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ADDRESS + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_PHONE_NUMBER + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_WEBSITE + " TEXT, " +
                        LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_SHARE_LINK + " TEXT"
                        + " );" ;

        // TODO : 51 ) Creating a table
        db.execSQL(CREATE_MOVIELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO : 52 ) Droping a table
        db.execSQL("DROP TABLE IF EXISTS " + LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME);

        // TODO : 53 ) Creating a table after droping a table
        onCreate(db);

    }
}
