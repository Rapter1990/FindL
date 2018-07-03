package com.germiyanoglu.android.findl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.modal.UserLocationRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

// TODO : 128 ) Creating ReviewItemListAdapter class to list users' review of location
public class ReviewItemListAdapter extends RecyclerView.Adapter<ReviewItemListAdapter.ReviewItemListAdapterViewHolder>{

    // TODO : 129 ) Defining attributes for adapter
    private Context mContext;
    private ArrayList<UserLocationRating> mUserLocationRatingArrayList;

    public ReviewItemListAdapter(Context context, ArrayList<UserLocationRating> ratingArrayList) {
        mContext = context;
        mUserLocationRatingArrayList = ratingArrayList;
    }

    @NonNull
    @Override
    public ReviewItemListAdapter.ReviewItemListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_location_review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new ReviewItemListAdapterViewHolder(view);
    }

    // TODO : 130 ) Showing user's review about location
    @Override
    public void onBindViewHolder(@NonNull ReviewItemListAdapter.ReviewItemListAdapterViewHolder holder, int position) {

        UserLocationRating userLocationRating = mUserLocationRatingArrayList.get(position);

        // User Profile Image
        Picasso.get().load(userLocationRating.getmUserProfilePictureUrl())
                .into(holder.mUserProfilePictureUrlImageView);

        // User Name
        holder.mUserNameTextView.setText(userLocationRating.getmUserName());

        // User's rating
        holder.mLocationRatingBarView.setRating(Float.parseFloat(String.valueOf(userLocationRating.getmUserPlaceRating())));

        // User's rating time
        holder.mUserRatingRelativeDateTimeTextView.setText(userLocationRating.getmUserRatingDateTime());

        // User's review
        holder.mUserReviewTextTextView.setText(userLocationRating.getmAuthorReviewText());
    }

    @Override
    public int getItemCount() {
        if(mUserLocationRatingArrayList == null){
            return 0;
        }
        return mUserLocationRatingArrayList.size();
    }

    public class ReviewItemListAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.location_review_user_profile_image_view)
        CircleImageView mUserProfilePictureUrlImageView;

        @BindView(R.id.location_review_user_name_text_view)
        TextView mUserNameTextView;

        @BindView(R.id.location_review_user_rating)
        MaterialRatingBar mLocationRatingBarView;

        @BindView(R.id.location_review_user_rating_time_date_text_view)
        TextView mUserRatingRelativeDateTimeTextView;

        @BindView(R.id.user_rating_description)
        TextView mUserReviewTextTextView;


        public ReviewItemListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
