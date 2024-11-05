package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.MovieService;
import com.theanimegroup.movie_ticket_booking_client.api.TicketService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Ticket;
import com.theanimegroup.movie_ticket_booking_client.models.entity.TicketDto;
import com.theanimegroup.movie_ticket_booking_client.models.request.TicketPurchaseRequest;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.models.response.TicketResponse;
import com.theanimegroup.movie_ticket_booking_client.util.TokenUtils;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDetailsActivity extends AppCompatActivity {
    private TicketService ticketService;
    private Button btnBook;
    private List<Ticket> ticketDtoList = new ArrayList<>();

    private TextView movieTextView;
    private TextView getShowtimeTextView;
    private TextView roomTextView;
    private TextView seatTextView;
    private TextView seatStatus;
    private TextView priceTextView;
    private Button btnConfirm;
    private int movieId = -1;
    private int seatId = -1;
    private int showTimeId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        // Initialize views
        movieTextView = findViewById(R.id.movie_title);
        getShowtimeTextView = findViewById(R.id.show_time);
        roomTextView = findViewById(R.id.movie_room);
        seatTextView = findViewById(R.id.seat_name);
        seatStatus = findViewById(R.id.status);
        priceTextView = findViewById(R.id.price);

        btnConfirm = findViewById(R.id.confirm_button);

        ticketService = APIUnit.getInstance().getTicketService();
        movieId = getIntent().getIntExtra("movieId", -1);
        seatId = getIntent().getIntExtra("seatId", -1);
        showTimeId = getIntent().getIntExtra("showTimeId", -1);

        loadTicketDetail();

        btnConfirm.setOnClickListener(v -> purchaseTicket());



        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void loadTicketDetail() {
        Call<ResponseObject<List<Ticket>>> call = ticketService.getTicketByMovieId(1);
        call.enqueue(new Callback<ResponseObject<List<Ticket>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Ticket>>> call, Response<ResponseObject<List<Ticket>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ticketDtoList.addAll(response.body().getData());
                    Ticket ticketDto = ticketDtoList.get(0);

                    movieTextView.setText(ticketDto.getMovieName());
                    movieTextView.setTextSize(24);
                    movieTextView.setTypeface(movieTextView.getTypeface(), Typeface.BOLD);

                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    String startDateText = "";
                    Date date = null;
                    try {
                        date = inputFormat.parse(ticketDto.getShowDateTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    assert date != null;
                    startDateText = "Show Time: " + outputFormat.format(date);
                    SpannableString startDateSpannable = new SpannableString(startDateText);
                    getShowtimeTextView.setText(startDateSpannable);
                    getShowtimeTextView.setTextSize(16);
                    getShowtimeTextView.setTextColor(Color.parseColor("#757575"));
                    startDateSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    String statusText = (ticketDto.getStatus() == 1) ? "Available" : "Unavailable";
                    seatStatus.setText("Status: " + statusText);
                    seatStatus.setTextSize(16);
                    seatStatus.setTextColor(ticketDto.getStatus() == 1 ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));

                    String room = getIntent().getStringExtra("room");
                    roomTextView.setText("Room: " + room);
                    roomTextView.setTextSize(16);
                    roomTextView.setTextColor(Color.parseColor("#212121"));

                    // Showtimes
                    seatTextView.setText("Seat: " + ticketDto.getSeatName());
                    seatTextView.setTextSize(16);
                    seatTextView.setTypeface(seatTextView.getTypeface(), Typeface.BOLD);
                    seatTextView.setTextColor(Color.parseColor("#757575"));

                    priceTextView.setText("Price: " + ticketDto.getPrice());
                    priceTextView.setTextSize(16);
                    priceTextView.setTypeface(priceTextView.getTypeface(), Typeface.BOLD);
                    priceTextView.setTextColor(Color.parseColor("#757575"));

                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Ticket>>> call, Throwable t) {
                Toast.makeText(TicketDetailsActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void purchaseTicket() {
        // Retrieve the access token
        String token = TokenUtils.getAuthToken(TicketDetailsActivity.this);
        if (token.isEmpty()) {
            Intent intent = new Intent(TicketDetailsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        String accessToken = String.format("Bearer %s", token);

        // Check if the movieId, seatId, and showTimeId are valid
        if (movieId != -1 && seatId != -1 && showTimeId != -1) {
            List<Integer> seatList = new ArrayList<>();
            seatList.add(seatId); // Add the seat ID to the list

            // Create the purchase request
            TicketPurchaseRequest request = new TicketPurchaseRequest(showTimeId, seatList);

            // Call the purchase ticket API
            Call<ResponseObject<TicketResponse>> call = ticketService.purchaseTicket(accessToken,request);
            call.enqueue(new Callback<ResponseObject<TicketResponse>>() {
                @Override
                public void onResponse(Call<ResponseObject<TicketResponse>> call, Response<ResponseObject<TicketResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(TicketDetailsActivity.this, "Ticket purchased successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TicketDetailsActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<TicketResponse>> call, Throwable t) {
                    Log.e("TicketPurchase", "Error: " + t.getMessage());
                    Toast.makeText(TicketDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(TicketDetailsActivity.this, "Movie ID or Seat ID is invalid.", Toast.LENGTH_SHORT).show();
        }
    }



}

