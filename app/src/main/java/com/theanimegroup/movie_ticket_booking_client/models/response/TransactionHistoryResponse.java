package com.theanimegroup.movie_ticket_booking_client.models.response;

import lombok.Getter;

@Getter
public class TransactionHistoryResponse {
    String movieName;
    String showDateTime;
    String room;
    String seatName;
    int ticketQuantity;
    double totalPrice;
    String time;
    String status;
    String transactionType;
}
