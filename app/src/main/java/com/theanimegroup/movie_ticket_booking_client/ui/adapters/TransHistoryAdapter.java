package com.theanimegroup.movie_ticket_booking_client.ui.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.response.TransactionResponse;

import java.util.List;

public class TransHistoryAdapter extends ArrayAdapter<TransactionResponse> {
    public TransHistoryAdapter(Context context, List<TransactionResponse> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction_history
                    , parent, false);
        }

        TransactionResponse transaction = getItem(position);

        TextView movieNameTextView = convertView.findViewById(R.id.movieNameTextView);
        TextView ticketQuantityTextView = convertView.findViewById(R.id.ticketQuantityTextView);
        TextView totalPriceTextView = convertView.findViewById(R.id.totalPriceTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);
        TextView transactionTypeTextView = convertView.findViewById(R.id.transactionTypeTextView);

        movieNameTextView.setText(transaction.movieName);
        ticketQuantityTextView.setText(String.valueOf(transaction.ticketQuantity));
        totalPriceTextView.setText(String.valueOf(transaction.totalPrice));
        timeTextView.setText(transaction.time);
        statusTextView.setText(transaction.status);
        transactionTypeTextView.setText(transaction.transactionType);

        return convertView;
    }
}

