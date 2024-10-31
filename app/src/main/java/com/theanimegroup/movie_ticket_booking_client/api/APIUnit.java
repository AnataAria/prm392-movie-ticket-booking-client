package com.theanimegroup.movie_ticket_booking_client.api;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIUnit {
    private static APIUnit instance;
    private AuthenticationService authenticationService;
    private MovieService movieService;
    private PingService pingService;
    public static APIUnit getInstance() {
        if (instance == null) {
            instance = new APIUnit();
            instance.init();
            instance.reloadConnection();
        }
        return instance;
    }
    public void init () {
        authenticationService = RetrofitClient.getInstance().create(AuthenticationService.class);
        movieService = RetrofitClient.getInstance().create(MovieService.class);
        pingService = RetrofitClient.getInstance().create(PingService.class);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public MovieService getMovieService() {
        return movieService;
    }
    public PingService getPingService() {
        return pingService;
    }

    public void reloadConnection () {
        getPingService().ping().enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (!response.isSuccessful()) {
                    System.exit(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                System.exit(0);
            }
        });
    }
}
