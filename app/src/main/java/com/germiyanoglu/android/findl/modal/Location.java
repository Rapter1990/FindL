package com.germiyanoglu.android.findl.modal;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.germiyanoglu.android.findl.data.LocationDetailContract;


// TODO : 122 ) Creating Location class
public class Location implements Parcelable {

    private static final String TAG = Location.class.getName();

    private String mLocationId;
    private Double mLocationLatitude;
    private Double mLocationLongitude;
    private String mLocationName;
    private String mLocationOpeningHourStatus;
    private Double mLocationRating;
    private String mLocationAddress;
    private String mLocationPhoneNumber;
    private String mLocationWebsite;
    private String mLocationShareLink;

    public Location(){
        super();
    }

    public Location(String mLocationId, Double mLocationLatitude,
                    Double mLocationLongitude, String mLocationName,
                    String mLocationOpeningHourStatus, Double mLocationRating,
                    String mLocationAddress, String mLocationPhoneNumber,
                    String mLocationWebsite) {
        this.mLocationId = mLocationId;
        this.mLocationLatitude = mLocationLatitude;
        this.mLocationLongitude = mLocationLongitude;
        this.mLocationName = mLocationName;
        this.mLocationOpeningHourStatus = mLocationOpeningHourStatus;
        this.mLocationRating = mLocationRating;
        this.mLocationAddress = mLocationAddress;
        this.mLocationPhoneNumber = mLocationPhoneNumber;
        this.mLocationWebsite = mLocationWebsite;
    }

    public Location(String mLocationId, Double mLocationLatitude,
                    Double mLocationLongitude, String mLocationName,
                    String mLocationOpeningHourStatus, Double mLocationRating,
                    String mLocationAddress, String mLocationPhoneNumber,
                    String mLocationWebsite, String mLocationShareLink) {
        this.mLocationId = mLocationId;
        this.mLocationLatitude = mLocationLatitude;
        this.mLocationLongitude = mLocationLongitude;
        this.mLocationName = mLocationName;
        this.mLocationOpeningHourStatus = mLocationOpeningHourStatus;
        this.mLocationRating = mLocationRating;
        this.mLocationAddress = mLocationAddress;
        this.mLocationPhoneNumber = mLocationPhoneNumber;
        this.mLocationWebsite = mLocationWebsite;
        this.mLocationShareLink = mLocationShareLink;
    }

    protected Location(Parcel in) {
        mLocationId = in.readString();
        if (in.readByte() == 0) {
            mLocationLatitude = null;
        } else {
            mLocationLatitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            mLocationLongitude = null;
        } else {
            mLocationLongitude = in.readDouble();
        }
        mLocationName = in.readString();
        mLocationOpeningHourStatus = in.readString();
        if (in.readByte() == 0) {
            mLocationRating = null;
        } else {
            mLocationRating = in.readDouble();
        }
        mLocationAddress = in.readString();
        mLocationPhoneNumber = in.readString();
        mLocationWebsite = in.readString();
        mLocationShareLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLocationId);
        if (mLocationLatitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLocationLatitude);
        }
        if (mLocationLongitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLocationLongitude);
        }
        dest.writeString(mLocationName);
        dest.writeString(mLocationOpeningHourStatus);
        if (mLocationRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLocationRating);
        }
        dest.writeString(mLocationAddress);
        dest.writeString(mLocationPhoneNumber);
        dest.writeString(mLocationWebsite);
        dest.writeString(mLocationShareLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getmLocationId() {
        return mLocationId;
    }

    public void setmLocationId(String mLocationId) {
        this.mLocationId = mLocationId;
    }

    public Double getmLocationLatitude() {
        return mLocationLatitude;
    }

    public void setmLocationLatitude(Double mLocationLatitude) {
        this.mLocationLatitude = mLocationLatitude;
    }

    public Double getmLocationLongitude() {
        return mLocationLongitude;
    }

    public void setmLocationLongitude(Double mLocationLongitude) {
        this.mLocationLongitude = mLocationLongitude;
    }

    public String getmLocationName() {
        return mLocationName;
    }

    public void setmLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }

    public String getmLocationOpeningHourStatus() {
        return mLocationOpeningHourStatus;
    }

    public void setmLocationOpeningHourStatus(String mLocationOpeningHourStatus) {
        this.mLocationOpeningHourStatus = mLocationOpeningHourStatus;
    }

    public Double getmLocationRating() {
        return mLocationRating;
    }

    public void setmLocationRating(Double mLocationRating) {
        this.mLocationRating = mLocationRating;
    }

    public String getmLocationAddress() {
        return mLocationAddress;
    }

    public void setmLocationAddress(String mLocationAddress) {
        this.mLocationAddress = mLocationAddress;
    }

    public String getmLocationPhoneNumber() {
        return mLocationPhoneNumber;
    }

    public void setmLocationPhoneNumber(String mLocationPhoneNumber) {
        this.mLocationPhoneNumber = mLocationPhoneNumber;
    }

    public String getmLocationWebsite() {
        return mLocationWebsite;
    }

    public void setmLocationWebsite(String mLocationWebsite) {
        this.mLocationWebsite = mLocationWebsite;
    }

    public String getmLocationShareLink() {
        return mLocationShareLink;
    }

    public void setmLocationShareLink(String mLocationShareLink) {
        this.mLocationShareLink = mLocationShareLink;
    }

    // TODO : 127 ) Defining from method to get information from database via cursor
    public static Location from(Cursor cursor) {

        try
        {
            int locationNameColumn = cursor.getColumnIndexOrThrow(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_NAME);
            int locationRatingColumn = cursor.getColumnIndexOrThrow(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_RATING);
            int locationStatusColumn = cursor.getColumnIndexOrThrow(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS);
            int locationAddressColumn = cursor.getColumnIndexOrThrow(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_OPENING_HOUR_STATUS);

            String locationName = cursor.getString(locationNameColumn);
            Double locationRating = cursor.getDouble(locationRatingColumn);
            String locationStatus = cursor.getString(locationStatusColumn);
            String locationAddress = cursor.getString(locationAddressColumn);


            Location locationFromCursor = new Location();

            locationFromCursor.setmLocationName(locationName);
            locationFromCursor.setmLocationRating(locationRating);
            locationFromCursor.setmLocationOpeningHourStatus(locationStatus);
            locationFromCursor.setmLocationAddress(locationAddress);

            return locationFromCursor;
        }
        catch (Exception e) {
            Log.d(TAG ,"Error : " + e);

        }

        return new Location();
    }
}
