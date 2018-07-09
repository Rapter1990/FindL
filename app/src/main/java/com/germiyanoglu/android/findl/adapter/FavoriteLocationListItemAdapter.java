package com.germiyanoglu.android.findl.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.activity.LocationDetailActivity;
import com.germiyanoglu.android.findl.data.LocationDetailContract;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.CursorRecyclerViewAdapter;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

// TODO : 124 ) Defining FavoriteLocationListItemAdapter to show locations as a favorites by defining CursorRecyclerViewAdapter
public class FavoriteLocationListItemAdapter extends CursorRecyclerViewAdapter<FavoriteLocationListItemAdapter.FavoriteLocationListItemAdapterViewHolder> {


    private Cursor mCurrentCursor;

    // TODO : 125 ) Creating Constructor
    public FavoriteLocationListItemAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }


    @NonNull
    @Override
    public FavoriteLocationListItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_location_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new FavoriteLocationListItemAdapterViewHolder(view);
    }

    // TODO : 126 ) Showing locations in favorites
    @Override
    public void onBindViewHolder(FavoriteLocationListItemAdapterViewHolder holder, Cursor cursor) {

        mCurrentCursor = cursor;

        Location locationDetails = Location.from(cursor);

        // Location Icon
        holder.mLocationIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.location_icon_color));

        // Location Distance
        holder.mLocationDistanceTextView.setText("");

        // Location Name
        holder.mLocationNameTextView.setText(locationDetails.getmLocationName());

        // Location Rating
        holder.mLocationRating.setRating(Float.parseFloat(String.valueOf(locationDetails.getmLocationRating())));

        // Location Address
        holder.mLocationAddressTextView.setText(locationDetails.getmLocationAddress());

        // Location Status

        // Location Status
        if (locationDetails.getmLocationOpeningHourStatus().equals("true")) {
            holder.mLocationOpenStatusTextView.setText(R.string.open_now);

        } else if (locationDetails.getmLocationOpeningHourStatus().equals("false")) {
            holder.mLocationOpenStatusTextView.setText(R.string.closed);

        } else {
            holder.mLocationOpenStatusTextView.setText(locationDetails
                    .getmLocationOpeningHourStatus());
        }

    }

    @Override
    public int getItemCount() {

        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    public class FavoriteLocationListItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.location_icon)
        ImageView mLocationIcon;

        @BindView(R.id.location_distance_text_view)
        TextView mLocationDistanceTextView;

        @BindView(R.id.location_name)
        TextView mLocationNameTextView;

        @BindView(R.id.user_rating)
        MaterialRatingBar mLocationRating;

        @BindView(R.id.location_address)
        TextView mLocationAddressTextView;

        @BindView(R.id.location_open_status)
        TextView mLocationOpenStatusTextView;


        public FavoriteLocationListItemAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.location_list_item)
        @Override
        public void onClick(View v) {
            if (UtilMethods.isNetworkAvailable(mContext)) {
                Intent currentLocationDetailIntent = new Intent(mContext, LocationDetailActivity.class);
                currentLocationDetailIntent.putExtra(GoogleMapApi.LOCATION_ID_TEXT,
                        mCurrentCursor.getString(
                                mCurrentCursor.getColumnIndex(LocationDetailContract.LocationDetailEntry.COLUMN_LOCATION_ID)));
                mContext.startActivity(currentLocationDetailIntent);

            }
        }
    }
}
