package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.request.AuthenticationRequest;
import com.theanimegroup.movie_ticket_booking_client.models.response.AuthenticationResponse;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/api/auth/login")
    Call<ResponseObject<AuthenticationResponse>> login (@Body AuthenticationRequest loginRequest);
}
