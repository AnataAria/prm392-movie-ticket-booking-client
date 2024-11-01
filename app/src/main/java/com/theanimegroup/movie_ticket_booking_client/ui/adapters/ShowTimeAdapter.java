package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;

import java.util.List;

public class ShowTimeAdapter extends BaseListAdapter<ShowTime> {

    public ShowTimeAdapter(Context context, List<ShowTime> showTimes) {
        super(context, showTimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_show_time, parent, false);
        }

        TextView id = convertView.findViewById(R.id.show_time_id);
        TextView showDateTime = convertView.findViewById(R.id.show_date_time);
        TextView room = convertView.findViewById(R.id.movie_room);

        ShowTime showTime = items.get(position);
        if (showTime != null) {
            id.setText(String.valueOf(showTime.getId()));
            showDateTime.setText(showTime.getShowDateTime());
            room.setText(showTime.getRoomName());
        } else {
            Log.e("ShowTimeAdapter", "ShowTime object is null for position: " + position);
        }

        return convertView;
    }

}