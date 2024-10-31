package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;

import java.util.List;

public class MovieHomeAdapter extends BaseListAdapter<Movie> {
    public MovieHomeAdapter(Context context, List<Movie> items) {
        super(context, items);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
