package com.germiyanoglu.android.findl.adapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.fragment.LocationInformationAbout;
import com.germiyanoglu.android.findl.fragment.LocationUserReview;

// TODO : 262 ) Defining ViewPagerAdapter to define two fragement in TabLayout
public class ViewPagerAdapter extends FragmentPagerAdapter {


    // TODO : 263 ) Defining two fragment as Bundle
    Bundle locationAboutFragment;
    Bundle locationReviewFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // TODO : 264 ) Adding two fragment
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LocationInformationAbout placeAboutDetailFragment = new LocationInformationAbout();
                placeAboutDetailFragment.setArguments(locationAboutFragment);
                return placeAboutDetailFragment;
            case 1:
                LocationUserReview placeReviewDetailFragment = new LocationUserReview();
                placeReviewDetailFragment.setArguments(locationReviewFragment);
                return placeReviewDetailFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    // TODO : 264 ) Setting coming bundles to defining bundles here
    public void setBundleData(Bundle aboutFragmentBundle, Bundle reviewFragmentBundle) {
        locationAboutFragment = aboutFragmentBundle;
        locationReviewFragment = reviewFragmentBundle;
    }

    // TODO : 265 ) Defining text of fragment
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Resources.getSystem().getString(R.string.about);
            case 1:
                return Resources.getSystem().getString(R.string.review);
        }
        return null;
    }
}
