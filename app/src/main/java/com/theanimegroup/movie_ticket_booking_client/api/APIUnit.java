package com.theanimegroup.movie_ticket_booking_client.api;

public class APIUnit {
    private static APIUnit instance;
    private AuthenticationService authenticationService;

    public static APIUnit getInstance() {
        if (instance == null) {
            instance = new APIUnit();
        }
        return instance;
    }
    public void init () {
        authenticationService = RetrofitClient.getInstance().create(AuthenticationService.class);
    }
}
