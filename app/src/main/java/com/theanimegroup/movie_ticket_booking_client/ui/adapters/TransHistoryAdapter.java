package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.response.TransactionResponse;

import java.util.List;

public class TransHistoryAdapter extends BaseListAdapter<TransactionResponse> {
    public TransHistoryAdapter(Context context, List<TransactionResponse> transactions) {
        super(context, transactions);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_transaction_history
                    , parent, false);
        }

        TransactionResponse transaction = getItem(position);

        TextView movieNameTextView = convertView.findViewById(R.id.movieNameTextView);
        TextView ticketQuantityTextView = convertView.findViewById(R.id.ticketQuantityTextView);
        TextView totalPriceTextView = convertView.findViewById(R.id.totalPriceTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);
        TextView transactionTypeTextView = convertView.findViewById(R.id.transactionTypeTextView);

        assert transaction != null;
        movieNameTextView.setText(transaction.movieName);
        ticketQuantityTextView.setText(String.valueOf(transaction.ticketQuantity));
        totalPriceTextView.setText(String.valueOf(transaction.totalPrice));
        timeTextView.setText(transaction.time);
        statusTextView.setText(transaction.status);
        transactionTypeTextView.setText(transaction.transactionType);

        return convertView;
    }
}

