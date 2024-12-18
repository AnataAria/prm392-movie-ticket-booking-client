package com.theanimegroup.movie_ticket_booking_client.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.theanimegroup.movie_ticket_booking_client.models.entity.Ticket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIUnit {
    private static APIUnit instance;
    private AuthenticationService authenticationService;
    private MovieService movieService;
    private PingService pingService;
    private SeatService seatService;
    private ShowTimeService showTimeService;
    private TransactionHistoryService transactionHistoryService;
    private CreateOrderService createOrderService;
    private TicketService ticketService;
    public static APIUnit getInstance() {
        if (instance == null) {
            instance = new APIUnit();
            instance.init();
        }
        return instance;
    }

    public void init() {
        authenticationService = RetrofitClient.getInstance().create(AuthenticationService.class);
        movieService = RetrofitClient.getInstance().create(MovieService.class);
        pingService = RetrofitClient.getInstance().create(PingService.class);
        seatService = RetrofitClient.getInstance().create(SeatService.class);
        showTimeService = RetrofitClient.getInstance().create(ShowTimeService.class);
        transactionHistoryService = RetrofitClient.getInstance().create(TransactionHistoryService.class);
        createOrderService = new CreateOrderService();
        ticketService = RetrofitClient.getInstance().create(TicketService.class);
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

    public SeatService getSeatService() {
        return seatService;
    }

    public ShowTimeService getShowTimeService() {
        return showTimeService;
    }

    public TransactionHistoryService getTransactionHistoryService() {
        return transactionHistoryService;
    }
    public TicketService getTicketService() {
        return ticketService;
    }

    public CreateOrderService getCreateOrderService() {
        return createOrderService;
    }

    public void reloadConnection() {
        getPingService().ping().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.exit(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.d("Init Connection Failed", t.getMessage(), t);
                System.exit(0);
            }
        });
    }
}
