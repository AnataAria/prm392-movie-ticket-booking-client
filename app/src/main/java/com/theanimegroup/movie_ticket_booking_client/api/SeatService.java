package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeatService {
    @GET("/api/Seat/GetAvailableSeatsByShowtimeId/{showtimeId}/movieId/{movieId}")
    Call<ResponseObject<List<Seat>>> getAvailbleSeat(@Path("showtimeId") int showTimeId, @Path("movieId") int movieId);
}
