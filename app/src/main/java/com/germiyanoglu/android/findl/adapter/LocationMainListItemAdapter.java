package com.germiyanoglu.android.findl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.germiyanoglu.android.findl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 101 ) Creating LocationMainListItemAdapter class to list location with respect to its name and location
public class LocationMainListItemAdapter extends RecyclerView.Adapter<LocationMainListItemAdapter.LocationListAdapterViewHolder> {

    private static final String TAG = LocationMainListItemAdapter.class.getName();

    // TODO : 109 ) Defining attributes used for adapter
    private Context mContext;
    private LinkedHashMap<String,Integer> locationList;
    private ArrayList<String> locationIconNameList;
    private ArrayList<Integer> locationImageList;

    // TODO : 110 ) Defining OnClickHandler variable
    private final LocationListItemAdapterOnClickHandler mClickHandler;

    // TODO : 111 ) Defining OnClickHandler interface
    public interface LocationListItemAdapterOnClickHandler {
        void onClick(String locationIconName);
    }

    // TODO : 112 ) Defining constructor
    public LocationMainListItemAdapter(Context context, LinkedHashMap<String, Integer> list, LocationListItemAdapterOnClickHandler mClickHandler) {
        mContext = context;
        locationList = list;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public LocationListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_location_main_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new LocationListAdapterViewHolder(view);

    }

    // TODO : 115 ) Showing Icon Image and its name of Location
    @Override
    public void onBindViewHolder(@NonNull LocationListAdapterViewHolder holder, int position) {

        locationIconNameList = getKeyValuesFrom(locationList);

        String name = locationIconNameList.get(position);

        holder.locationIconName.setText(name);


        locationImageList = getValuesFrom(locationList);

        int locationDrawableImage = locationImageList.get(position);

        Picasso.get().load(locationDrawableImage)
                .into(holder.locationIcon);

    }

    // TODO : 113 ) Getting item count of locationList
    @Override
    public int getItemCount() {
        if(locationList == null){
            return 0;
        }
        return locationList.size();
    }

    // TODO : 114 ) Defining LocationListAdapterViewHolder to define Location Icon and its name
    public class LocationListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.location_icon)
        ImageView locationIcon;

        @BindView(R.id.location_icon_text_view)
        TextView locationIconName;


        public LocationListAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            locationIconNameList = getKeyValuesFrom(locationList);

            String locationIconName = locationIconNameList.get(adapterPosition);
            mClickHandler.onClick(locationIconName);

        }
    }


    public ArrayList<String> getKeyValuesFrom(LinkedHashMap<String,Integer> list){

        ArrayList<String> keyValues = new ArrayList<String>();

        for(Map.Entry<String,Integer> t : list.entrySet()) {
            keyValues.add(t.getKey());
        }
        return keyValues;
    }

    public ArrayList<Integer> getValuesFrom(LinkedHashMap<String,Integer> list){

        ArrayList<Integer> values = new ArrayList<Integer>();

        for(Map.Entry<String,Integer> t : list.entrySet()) {
            values.add(t.getValue());
        }
        return values;
    }


}
