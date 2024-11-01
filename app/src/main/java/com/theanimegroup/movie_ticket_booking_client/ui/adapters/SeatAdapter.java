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
import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;

import java.util.ArrayList;

public class SeatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Seat> seats;

    public SeatAdapter(Context context, ArrayList<Seat> seats) {
        this.context = context;
        this.seats = seats;
    }

    @Override
    public int getCount() {
        return seats.size();
    }
    @Override
    public Object getItem(int position) {
        return seats.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_seat, parent, false);
        }

        TextView id = convertView.findViewById(R.id.seat_id);
        TextView seatNumber = convertView.findViewById(R.id.seat_number);
        TextView cinema = convertView.findViewById(R.id.cinema_name);

        Seat seat = seats.get(position);
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