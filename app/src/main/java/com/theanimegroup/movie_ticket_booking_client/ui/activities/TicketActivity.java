package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.TicketService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.TicketDto;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.TicketAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {
    private TicketService ticketService;
    private TicketAdapter adapter;
    private ListView listView;
    private List<TicketDto> ticketDtoList = new ArrayList<>();
    private TextView title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entity);

        listView = findViewById(R.id.listView);
        ticketService = APIUnit.getInstance().getTicketService();

        loadAvailableSeats();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TicketDto selectedTicketDto = ticketDtoList.get(position);
            Intent intent = new Intent(TicketActivity.this, TicketDetailsActivity.class);
            String room = getIntent().getStringExtra("room");
            intent.putExtra("room", room);
            intent.putExtra("ticketId", selectedTicketDto.getId());
            intent.putExtra("seatId", selectedTicketDto.getSeatName());
            startActivity(intent);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void loadAvailableSeats() {
        int movieId = getIntent().getIntExtra("movieId", -1);
        int showTimeId = getIntent().getIntExtra("showTimeId", -1);

        Call<ResponseObject<List<TicketDto>>> call = ticketService.getTicketByMovieId(movieId);
        call.enqueue(new Callback<ResponseObject<List<TicketDto>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<TicketDto>>> call, Response<ResponseObject<List<TicketDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    ticketDtoList.clear();
                    ticketDtoList.addAll(response.body().getData());
                    adapter = new TicketAdapter(TicketActivity.this, new ArrayList<>(ticketDtoList));
                    listView.setAdapter(adapter);
                    Log.i("SeatActivity", "Seats loaded successfully");
                } else {
                    Log.e("SeatActivity", "Response error: " + (response.body() != null ? response.body().getError() : "No body"));
                    Toast.makeText(TicketActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<TicketDto>>> call, Throwable t) {
                Log.e("SeatActivity", "API call failed", t);
                Toast.makeText(TicketActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
