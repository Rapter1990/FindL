package com.germiyanoglu.android.findl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.adapter.ReviewItemListAdapter;
import com.germiyanoglu.android.findl.modal.UserLocationRating;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 148 ) Creating LocationUserReview class to show each user's review about a certain location
public class LocationUserReview extends Fragment {

    private static final String TAG = LocationUserReview.class.getName();

    // TODO : 149 ) Defining attributes of Recyleview

    @BindView(R.id.user_location_review_recyleview)
    RecyclerView mRecyclerView;

    @BindView(R.id.user_location_review_empty_view)
    TextView mEmptyText;

    // TODO : 150 ) Defining arraylist and adapter used for LocationUserReview
    private ArrayList<UserLocationRating> mUserLocationRatings = new ArrayList<>();
    private ReviewItemListAdapter mReviewItemListAdapter;

    // TODO : 151 ) Creating bundle with creating LocationUserReview
    public LocationUserReview(){
        super();
        Log.d(TAG, "LocationUserReview Bundle is working");
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView Bundle is working");
        Context context = container.getContext();
        int layoutIdForListItem = R.layout.fragment_user_review_location;
        inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, container, shouldAttachToParentImmediately);
        ButterKnife.bind(this,view);

        mUserLocationRatings = getUserLocationRatingListFromBundle();

        // TODO : 153 ) Determine whether the review list is deemed to show or not in Recyleview
        if(mUserLocationRatings != null){
            Log.d(TAG, "mUserLocationRatings is not null");
            int orientation = GridLayout.VERTICAL;
            int span = getResources().getInteger(R.integer.gridlayout_review_span);
            boolean reverseLayout = false;
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), span, orientation, reverseLayout);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            mEmptyText.setVisibility(View.GONE);

            mReviewItemListAdapter = new ReviewItemListAdapter(getActivity(),mUserLocationRatings);

            mRecyclerView.setAdapter(mReviewItemListAdapter);

        }else{
            Log.d(TAG, "mUserLocationRatings is null");
            mRecyclerView.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
        }


        return view;
    }


    // TODO : 152 ) Defining getUserLocationRatingListFromBundle class to get Arraylist from previous activity
    private ArrayList<UserLocationRating> getUserLocationRatingListFromBundle(){
        Log.d(TAG, "getUserLocationRatingListFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            return bundle.getParcelableArrayList(GoogleMapApi.CURRENT_LOCATION_USER_RATING);
        }else{
            return null;
        }
    }

}
