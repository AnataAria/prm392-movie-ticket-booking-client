package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.models.response.TransactionHistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TransactionHistoryService {
    @GET("/api/2024-11-11/transactionsistories/list/account")
    Call<ResponseObject<List<TransactionHistoryResponse>>> getUserTransaction(@Header("Authorization") String token);
}
