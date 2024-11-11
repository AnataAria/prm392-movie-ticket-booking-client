package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.models.request.AuthenticationRequest;
import com.theanimegroup.movie_ticket_booking_client.models.request.RegisterRequest;
import com.theanimegroup.movie_ticket_booking_client.models.response.AccountResponseBasic;
import com.theanimegroup.movie_ticket_booking_client.models.response.AuthenticationResponse;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface AuthenticationService {
    @POST("/api/2024-11-11/auth/login")
    Call<ResponseObject<AuthenticationResponse>> login(@Body AuthenticationRequest loginRequest);

    @POST("/api/2024-11-11/auth/register")
    Call<ResponseObject<Object>> register(@Body RegisterRequest registerRequest);

    @GET("/api/2024-11-11/auth/who-am-i")
    Call<ResponseObject<AccountResponseBasic>> me(@Header("Authorization") String header);
    @PUT("/api/2024-11-11/account/wallet")
    Call<ResponseObject<Object>> updateWallet(@Header("Authorization") String header, @Query("wallet") Double wallet);
}
