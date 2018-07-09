package com.germiyanoglu.android.findl.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

// TODO 306 ) Defining FavoriteLocationWidgetService to connect between WidgetProvider and Adapter
public class FavoriteLocationWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteLocationAdapter(this);
    }
}
