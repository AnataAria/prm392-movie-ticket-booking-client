package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShowTimeService {
    @GET("/api/2024-11-11/showtimes/movieid/{movieId}")
    Call<ResponseObject<List<ShowTime>>> getShowTimeByMovieId(@Path("movieId") int movieId);
}
