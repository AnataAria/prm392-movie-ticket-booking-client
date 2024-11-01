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
import com.theanimegroup.movie_ticket_booking_client.api.ShowTimeService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.ShowTimeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowTimeActivity extends AppCompatActivity {
    private ShowTimeAdapter adapter;
    private ListView listView;
    public ShowTimeService showTimeService;
    private List<ShowTime> showTimeList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entity);

        listView = findViewById(R.id.listView);
        showTimeService = RetrofitClient.getInstance().create(ShowTimeService.class);

        loadMovieShowTime();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ShowTime selectedShowTime = showTimeList.get(position);
            Intent intent = new Intent(ShowTimeActivity.this, SeatActivity.class);
            //intent.putExtra("movieId", selectedMovie.getId()); // Pass movie ID or other data if needed
            intent.putExtra("showTimeId", selectedShowTime.getId());
            int movieId = getIntent().getIntExtra("movieId", -1);
            intent.putExtra("movieId", movieId);
            startActivity(intent);
        });
    }

    private void loadMovieShowTime() {
        int movieId = getIntent().getIntExtra("movieId", -1);

        Call<ResponseObject<List<ShowTime>>> call = showTimeService.getShowTimeByMovieId(movieId);
        call.enqueue(new Callback<ResponseObject<List<ShowTime>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<ShowTime>>> call, Response<ResponseObject<List<ShowTime>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    showTimeList.clear();
                    showTimeList.addAll(response.body().getData());
                    adapter = new ShowTimeAdapter(ShowTimeActivity.this, new ArrayList<>(showTimeList));
                    listView.setAdapter(adapter);
                    Log.i("MovieActivity", "Movies loaded successfully");
                } else {
                    Log.e("MovieActivity", "Response error: " + (response.body() != null ? response.body().getError() : "No body"));
                    Toast.makeText(ShowTimeActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<ShowTime>>> call, Throwable t) {
                Log.e("MovieActivity", "API call failed", t);
                Toast.makeText(ShowTimeActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}