package com.germiyanoglu.android.findl.modal;

import android.os.Parcel;
import android.os.Parcelable;

// TODO : 123 ) Creating UserLocationRating
public class UserLocationRating implements Parcelable {

    private String mUserName;
    private String mUserProfilePictureUrl;
    private Double mUserPlaceRating;
    private String mUserRatingDateTime;
    private String mAuthorReviewText;

    public UserLocationRating(String mUserName, String mUserProfilePictureUrl,
                              Double mUserPlaceRating, String mUserRatingDateTime,
                              String mAuthorReviewText) {
        this.mUserName = mUserName;
        this.mUserProfilePictureUrl = mUserProfilePictureUrl;
        this.mUserPlaceRating = mUserPlaceRating;
        this.mUserRatingDateTime = mUserRatingDateTime;
        this.mAuthorReviewText = mAuthorReviewText;
    }


    protected UserLocationRating(Parcel in) {
        mUserName = in.readString();
        mUserProfilePictureUrl = in.readString();
        if (in.readByte() == 0) {
            mUserPlaceRating = null;
        } else {
            mUserPlaceRating = in.readDouble();
        }
        mUserRatingDateTime = in.readString();
        mAuthorReviewText = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
        dest.writeString(mUserProfilePictureUrl);
        if (mUserPlaceRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mUserPlaceRating);
        }
        dest.writeString(mUserRatingDateTime);
        dest.writeString(mAuthorReviewText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserLocationRating> CREATOR = new Creator<UserLocationRating>() {
        @Override
        public UserLocationRating createFromParcel(Parcel in) {
            return new UserLocationRating(in);
        }

        @Override
        public UserLocationRating[] newArray(int size) {
            return new UserLocationRating[size];
        }
    };

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserProfilePictureUrl() {
        return mUserProfilePictureUrl;
    }

    public void setmUserProfilePictureUrl(String mUserProfilePictureUrl) {
        this.mUserProfilePictureUrl = mUserProfilePictureUrl;
    }

    public Double getmUserPlaceRating() {
        return mUserPlaceRating;
    }

    public void setmUserPlaceRating(Double mUserPlaceRating) {
        this.mUserPlaceRating = mUserPlaceRating;
    }

    public String getmUserRatingDateTime() {
        return mUserRatingDateTime;
    }

    public void setmUserRatingDateTime(String mUserRatingDateTime) {
        this.mUserRatingDateTime = mUserRatingDateTime;
    }

    public String getmAuthorReviewText() {
        return mAuthorReviewText;
    }

    public void setmAuthorReviewText(String mAuthorReviewText) {
        this.mAuthorReviewText = mAuthorReviewText;
    }
}
