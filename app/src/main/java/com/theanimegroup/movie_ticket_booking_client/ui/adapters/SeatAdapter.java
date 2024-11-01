package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends BaseListAdapter<Seat> {

    public SeatAdapter(Context context, List<Seat> seats) {
        super(context,seats);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_seat, parent, false);
        }

        TextView id = convertView.findViewById(R.id.seat_id);
        TextView seatNumber = convertView.findViewById(R.id.seat_number);
        TextView cinema = convertView.findViewById(R.id.cinema_name);

        Seat seat = items.get(position);
        if (seat != null) {
            id.setText(String.valueOf(seat.getSeatId()));
            seatNumber.setText(seat.getSeatNumber());
            cinema.setText(seat.getCinemaName());
        } else {
            Log.e("ShowTimeAdapter", "ShowTime object is null for position: " + position);
        }

        return convertView;
    }

}