package com.germiyanoglu.android.findl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.germiyanoglu.android.findl.R;
import com.germiyanoglu.android.findl.modal.Location;
import com.germiyanoglu.android.findl.utils.GoogleMapApi;
import com.germiyanoglu.android.findl.utils.UtilMethods;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

// TODO : 116 ) Creating LocationItemListAdapter for the design of locations' list which is near me
public class LocationItemListAdapter extends RecyclerView.Adapter<LocationItemListAdapter.LocationListAdapterViewHolder>{

    private static final String TAG = LocationItemListAdapter.class.getName();

    // TODO : 116 ) Defining attributes used for adapter
    private Context mContext;
    private ArrayList<Location> mNearByLocationArrayList = new ArrayList<>();

    // TODO : 117 ) Defining OnClickHandler variable
    private final NearLocationListItemAdapterOnClickHandler mClickHandler;


    // TODO : 118 ) Defining OnClickHandler interface
    public interface NearLocationListItemAdapterOnClickHandler {
        void onClick(Location nearLocationData);
    }


    public LocationItemListAdapter(Context context, ArrayList<Location> nearByPlaceArrayList, NearLocationListItemAdapterOnClickHandler mClickHandler) {
        mContext = context;
        mNearByLocationArrayList = nearByPlaceArrayList;
        this.mClickHandler = mClickHandler;
    }


    @NonNull
    @Override
    public LocationListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_location_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new LocationListAdapterViewHolder(view);
    }

    // TODO : 120 ) Showing location attributes
    @Override
    public void onBindViewHolder(@NonNull LocationListAdapterViewHolder holder, int position) {

        // Location Image Color
        holder.mLocationIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.location_icon_color));

        // Calculate Current Location
        String currentLocation = mContext.getSharedPreferences(
                GoogleMapApi.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0)
                .getString(GoogleMapApi.CURRENT_LOCATION_DATA, null);

        String[] currentLocationLatitudeAndLongitude = currentLocation.split(",");

        Double distance = UtilMethods.CalculationByDistance(
                Double.parseDouble(currentLocationLatitudeAndLongitude[0]),
                Double.parseDouble(currentLocationLatitudeAndLongitude[1]),
                mNearByLocationArrayList.get(position).getmLocationLatitude(),
                mNearByLocationArrayList.get(position).getmLocationLongitude()) / 1000;

        Log.d(TAG,"Distance Calculation : " + distance);

        if ( !(distance instanceof Double) ) {
            distance = distance.doubleValue();
        }

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#.##", symbols);

        Double totalDistance = 0.0;
        try {
            totalDistance = (double) format.parse(format.format(distance));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ( !(totalDistance instanceof Double) ) {
            totalDistance = totalDistance.doubleValue();
        }

        // Location Distance
        String distanceBetweenTwoPlace = String.valueOf(totalDistance);
        holder.mLocationDistanceTextView.setText(
                mContext.getResources().getString(R.string.location_distance_start) + distanceBetweenTwoPlace
                        + mContext.getResources().getString(R.string.location_distance_end));

        // Location Name
        holder.mLocationNameTextView.setText(mNearByLocationArrayList.get(position).getmLocationName());

        // Location User's Rating
        holder.mLocationRating.setRating(Float.parseFloat(String.valueOf(mNearByLocationArrayList.get(position)
                .getmLocationRating())));

        // Location Address
        holder.mLocationAddressTextView.setText(mNearByLocationArrayList.get(position).getmLocationAddress());

        // Location Status
        if (mNearByLocationArrayList.get(position).getmLocationOpeningHourStatus().equals("true")) {
            holder.mLocationOpenStatusTextView.setText(R.string.open_now);

        } else if (mNearByLocationArrayList.get(position).getmLocationOpeningHourStatus().equals("false")) {
            holder.mLocationOpenStatusTextView.setText(R.string.closed);

        } else {
            holder.mLocationOpenStatusTextView.setText(mNearByLocationArrayList.get(position)
                    .getmLocationOpeningHourStatus());
        }

    }

    @Override
    public int getItemCount() {
        if(mNearByLocationArrayList == null){
            return 0;
        }
        return mNearByLocationArrayList.size();
    }


    // TODO : 119 ) Defining LocationListAdapterViewHolder to define Location information
    public class LocationListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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



        public LocationListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.location_list_item)
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Location nearLocationDetail = mNearByLocationArrayList.get(adapterPosition);
            mClickHandler.onClick(nearLocationDetail);
        }
    }
}
