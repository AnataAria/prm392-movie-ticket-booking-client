package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.SeatService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.SeatAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatActivity extends AppCompatActivity {
    public SeatService seatService;
    private SeatAdapter adapter;
    private ListView listView;
    private final List<Seat> seatList = new ArrayList<>();
    private int movieId = -1;
    private int showTimeId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entity);
        movieId = getIntent().getIntExtra("movieId", -1);
        showTimeId = getIntent().getIntExtra("showTimeId", -1);
        listView = findViewById(R.id.listView);
        seatService = APIUnit.getInstance().getSeatService();

        loadAvailableSeat();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Seat select = seatList.get((int)id);
            Intent intent = new Intent(SeatActivity.this, TicketDetailsActivity.class);
            intent.putExtra("movieId", movieId);
            intent.putExtra("showTimeId", showTimeId);
            intent.putExtra("seatId", select.getSeatId());
            startActivity(intent);
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void loadAvailableSeat() {
        Call<ResponseObject<List<Seat>>> call = seatService.getAvailbleSeat(showTimeId, movieId);
        call.enqueue(new Callback<ResponseObject<List<Seat>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Seat>>> call, Response<ResponseObject<List<Seat>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    seatList.clear();
                    seatList.addAll(response.body().getData());
                    adapter = new SeatAdapter(SeatActivity.this, new ArrayList<>(seatList));
                    listView.setAdapter(adapter);
                    Log.i("MovieActivity", "Movies loaded successfully");
                } else {
                    Log.e("MovieActivity", "Response error: " + (response.body() != null ? response.body().getError() : "No body"));
                    Toast.makeText(SeatActivity.this, "Ticket sold out!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Seat>>> call, Throwable t) {
                Log.e("MovieActivity", "API call failed", t);
                Toast.makeText(SeatActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

