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
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.util.TokenUtils;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDetailsActivity extends AppCompatActivity {
    private TicketService ticketService;
    private Button btnBook;
    private List<TicketDto> ticketDtoList = new ArrayList<>();

    private TextView movieTextView;
    private TextView getShowtimeTextView;
    private TextView roomTextView;
    private TextView seatTextView;
    private TextView seatStatus;
    private TextView priceTextView;
    private Button btnConfirm;
    private int currentTicketId = -1;
    private int movieId = -1;

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
        int movieId = getIntent().getIntExtra("movieId", -1);
        currentTicketId = getIntent().getIntExtra("ticketId", -1);

        loadTicketDetail();



        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void loadTicketDetail() {
        Call<ResponseObject<List<TicketDto>>> call = ticketService.getTicketByMovieId(movieId);
        call.enqueue(new Callback<ResponseObject<List<TicketDto>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<TicketDto>>> call, Response<ResponseObject<List<TicketDto>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ticketDtoList.addAll(response.body().getData());
                    TicketDto ticketDto = ticketDtoList.get(0);

                    movieTextView.setText(ticketDto.getMovieName());
                    movieTextView.setTextSize(24);
                    movieTextView.setTypeface(movieTextView.getTypeface(), Typeface.BOLD);

                    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    String startDateText = "Show Time: " + dateFormat.format(ticketDto.getShowDateTime());
                    SpannableString startDateSpannable = new SpannableString(startDateText);
                    startDateSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    getShowtimeTextView.setText(startDateSpannable);
                    getShowtimeTextView.setTextSize(16);
                    getShowtimeTextView.setTextColor(Color.parseColor("#757575"));


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
            public void onFailure(Call<ResponseObject<List<TicketDto>>> call, Throwable t) {
                Toast.makeText(TicketDetailsActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }



}

