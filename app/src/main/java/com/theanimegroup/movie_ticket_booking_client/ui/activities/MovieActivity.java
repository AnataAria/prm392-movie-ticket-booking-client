package com.theanimegroup.movie_ticket_booking_client.ui.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.MovieService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
@NoArgsConstructor
public class MovieActivity extends AppCompatActivity {
    public MovieService movieService;
    private MovieAdapter adapter;
    private ListView listView;
    private List<Movie> movieList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entity);

        listView = findViewById(R.id.listView);
        movieService = APIUnit.getInstance().getMovieService();

        loadMovies();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie selectedMovie = movieList.get(position);
            Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
            intent.putExtra("movieId", selectedMovie.getId());
            startActivity(intent);
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void loadMovies() {
        Call<ResponseObject<List<Movie>>> call = movieService.getAllMovies();
        call.enqueue(new Callback<ResponseObject<List<Movie>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Movie>>> call, Response<ResponseObject<List<Movie>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getData());
                    adapter = new MovieAdapter(MovieActivity.this, movieList);
                    listView.setAdapter(adapter);
                    Log.i("MovieActivity", "Movies loaded successfully");
                } else {
                    Log.e("MovieActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Movie>>> call, Throwable t) {
                Log.e("MovieActivity", "API call failed", t);
                Toast.makeText(MovieActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}