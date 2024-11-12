package com.theanimegroup.movie_ticket_booking_client.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PingService {
    @GET("/api/2024-11-11/pings/server")
    Call<Object> ping();
}
