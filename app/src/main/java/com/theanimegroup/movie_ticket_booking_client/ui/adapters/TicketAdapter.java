package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
import com.theanimegroup.movie_ticket_booking_client.models.entity.TicketDto;

import java.util.List;

public class TicketAdapter extends BaseListAdapter<TicketDto> {

    public TicketAdapter(Context context, List<TicketDto> tickets) {
        super(context, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_seat, parent, false);
        }

        TextView id = convertView.findViewById(R.id.seat_id);
        TextView seatNumber = convertView.findViewById(R.id.seat_number);
        TextView price = convertView.findViewById(R.id.cinema_name);

        TicketDto ticketDto = items.get(position);
        if (ticketDto != null) {
            id.setText(String.valueOf(ticketDto.getId()));
            seatNumber.setText(ticketDto.getSeatName());
            price.setText(ticketDto.getPrice());
        } else {
            Log.e("ShowTimeAdapter", "ShowTime object is null for position: " + position);
        }

        return convertView;
    }

}