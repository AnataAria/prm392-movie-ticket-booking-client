package com.theanimegroup.movie_ticket_booking_client.api;

import com.theanimegroup.movie_ticket_booking_client.ui.activities.MovieActivity;

public class APIUnit {
    private static APIUnit instance;
    private AuthenticationService authenticationService;
    private MovieService movieService;
    public static APIUnit getInstance() {
        if (instance == null) {
            instance = new APIUnit();
            instance.init();
        }
        return instance;
    }
    public void init () {
        authenticationService = RetrofitClient.getInstance().create(AuthenticationService.class);
        movieService = RetrofitClient.getInstance().create(MovieService.class);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public MovieService getMovieService() {
        return movieService;
    }
}
