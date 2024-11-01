package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.RetrofitClient;
import com.theanimegroup.movie_ticket_booking_client.api.SeatService;
import com.theanimegroup.movie_ticket_booking_client.api.ShowTimeService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Seat;
import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.SeatAdapter;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.ShowTimeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatActivity extends AppCompatActivity {
    private SeatAdapter adapter;
    private ListView listView;
    public SeatService seatService;
    private List<Seat> seatList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entity);

        listView = findViewById(R.id.listView);
        seatService = RetrofitClient.getInstance().create(SeatService.class);

        loadAvailbleSeat();
        listView.setOnItemClickListener((parent, view, position, id) -> {

        });
    }

    private void loadAvailbleSeat() {
        int movieId = getIntent().getIntExtra("movieId", -1);
        int showTimeId = getIntent().getIntExtra("showTimeId", -1);

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
                    Toast.makeText(SeatActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
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

