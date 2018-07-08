package com.germiyanoglu.android.findl.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

// TODO : 54 ) Creating Content Provider for Movie in terms of CRUD operations.
public class LocationDetailContentProvider extends ContentProvider {

    public static final String TAG = LocationDetailContentProvider.class.getSimpleName();

    // TODO : 55 ) Creating MOVIE AND MOVIE_WITH_ID variables for URI Matcher
    public static final int LOCATION = 100;
    public static final int LOCATION_WITH_ID = 101;

    // TODO : 56 ) Defining URI Matcher after getting path in contract of location class
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // TODO : 57 ) Defining MovieDpHelper
    private LocationDetailHelper mlocationHelper;

    // TODO : 58 ) Creating a MoviesDpHelper object in onCreate method and return true
    @Override
    public boolean onCreate() {
        mlocationHelper = new LocationDetailHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // TODO : 60 ) Preparing a database as a readable operation
        final SQLiteDatabase db = mlocationHelper.getReadableDatabase();

        // TODO : 61 ) Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // TODO : 62 ) Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case LOCATION:
                retCursor =  db.query(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case LOCATION_WITH_ID:
                selection = LocationDetailContract.LocationDetailEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                retCursor = db.query(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // TODO : 63 ) Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);


        // TODO : 64 ) Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        // TODO : 84 ) Returning type of uri with LOCATION and LOCATION_WITH_ID
        int matchPlaceUri = sUriMatcher.match(uri);

        switch (matchPlaceUri) {
            case LOCATION:
                return LocationDetailContract.LocationDetailEntry.CONTENT_LIST_TYPE;
            case LOCATION_WITH_ID:
                return LocationDetailContract.LocationDetailEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri + " With match " + matchPlaceUri);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // TODO : 65 ) Preparing a database as a writable operation
        final SQLiteDatabase db= mlocationHelper.getWritableDatabase();

        // TODO : 66 ) Write URI match code and set a variable to return a Uri
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        // TODO : 67 ) Insert Query for the tasks directory and write a default case
        switch(match) {
            case LOCATION:
                long id = db.insert(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(LocationDetailContract.LocationDetailEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // TODO : 68 ) Set a notification URI if a task was inserted and return that Uri
        getContext().getContentResolver().notifyChange(uri, null);

        // TODO : 69 ) return that Uri
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // TODO : 70 ) Preparing a database as a writable operation
        final SQLiteDatabase db = mlocationHelper.getWritableDatabase();

        // TODO : 71 ) Write URI match code and set a variable to return a taskDeleted
        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        // TODO : 72 ) Delete Query for the tasks directory and write a default case
        switch (match) {
            case LOCATION :
                tasksDeleted = db.delete(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case LOCATION_WITH_ID:
                //Delete single Place detail from the Database
                selection = LocationDetailContract.LocationDetailEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                tasksDeleted = db.delete(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // TODO : 73 ) Checking whether tasksDeleted has a value or not
        if (tasksDeleted != 0) {

            // TODO : 74 ) Set a notification URI if a task was deleted
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // TODO : 75 ) Returning taskDeleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        // TODO : 76 ) Preparing a database as a writable operation
        final SQLiteDatabase db=mlocationHelper.getWritableDatabase();

        // TODO : 77 ) Write URI match code and set a variable to return a tasksUpdated
        int match = sUriMatcher.match(uri);
        int tasksUpdated;

        // TODO : 78 ) Update Query for the tasks directory and write a case for location's id
        switch (match) {
            case LOCATION_WITH_ID:
                //update a single task by getting the id
//                String id = uri.getPathSegments().get(1);
                //using selections
                tasksUpdated = db.update(LocationDetailContract.LocationDetailEntry.LOCATION_TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{selection});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // TODO : 79 ) Checking whether tasksDeleted has a value or not
        if (tasksUpdated != 0) {

            // TODO : 80 ) Set a notification URI if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // TODO : 81 ) Returning number of tasks updated
        return tasksUpdated;
    }




    // TODO : 59 ) Returning uri matcher adding LOCATION and LOCATION_WITH_ID
    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(LocationDetailContract.CONTENT_AUTHORITY, LocationDetailContract.PATH_LOCATION, LOCATION);
        uriMatcher.addURI(LocationDetailContract.CONTENT_AUTHORITY, LocationDetailContract.PATH_LOCATION + "/#", LOCATION_WITH_ID);
        return uriMatcher;
    }
}
