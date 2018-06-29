package com.germiyanoglu.android.findl.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

// TODO 39 ) Creating LocationDetailContract for defining each column of LOCATION that will be registered in the database.
public class LocationDetailContract {

    public static final String TAG = LocationDetailContract.class.getName();

    // TODO : 40) Defining authority of project
    public static final String CONTENT_AUTHORITY = "com.germiyanoglu.android.findl";

    // TODO : 41 ) Defining content with authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // TODO : 46 ) Defining path of content
    public static final String PATH_LOCATION = "locations";

    // TODO : 47 ) Defining LocationDetailEntry to define movies' attributes as a column
    public static final class LocationDetailEntry implements BaseColumns {

        public static final Uri    CONTENT_URI      = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String LOCATION_TABLE_NAME = "location_detail";

        public static final String _ID = "_id";
        public static final String COLUMN_LOCATION_ID = "location_id";
        public static final String COLUMN_LOCATION_LATITUDE = "location_latitude";
        public static final String COLUMN_LOCATION_LONGITUDE = "location_longitude";
        public static final String COLUMN_LOCATION_NAME = "location_name";
        public static final String COLUMN_LOCATION_OPENING_HOUR_STATUS = "location_opening_hour_status";
        public static final String COLUMN_LOCATION_RATING = "location_rating";
        public static final String COLUMN_LOCATION_ADDRESS = "location_address";
        public static final String COLUMN_LOCATION_PHONE_NUMBER = "location_phone_number";
        public static final String COLUMN_LOCATION_WEBSITE = "location_website";
        public static final String COLUMN_LOCATION_SHARE_LINK = "location_share_link";


        // TODO : 82 ) The MIME type of the Location
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        // TODO : 83 ) The MIME type of the Single Location
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

    }
}
