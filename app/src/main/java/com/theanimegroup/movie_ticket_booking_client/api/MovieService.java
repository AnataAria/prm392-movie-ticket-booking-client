package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("/api/Account")
    Call<Movie[]> getAllMovies ();
}


