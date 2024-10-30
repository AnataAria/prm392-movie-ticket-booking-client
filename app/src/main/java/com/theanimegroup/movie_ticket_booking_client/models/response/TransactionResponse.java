package com.theanimegroup.movie_ticket_booking_client.models.response;

import java.util.Date;

public class TransactionResponse {
    public String movieName;
    public int ticketQuantity;
    public int totalPrice;
    public String time;
    public String status;
    public String transactionType;

    public TransactionResponse(String movieName, int ticketQuantity, int totalPrice, String time, String status, String transactionType) {
        this.movieName = movieName;
        this.ticketQuantity = ticketQuantity;
        this.totalPrice = totalPrice;
        this.time = time;
        this.status = status;
        this.transactionType = transactionType;
    }
}

