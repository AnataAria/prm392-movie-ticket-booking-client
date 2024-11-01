package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.MovieService;
import com.theanimegroup.movie_ticket_booking_client.api.RetrofitClient;
import com.theanimegroup.movie_ticket_booking_client.api.ShowTimeService;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;
import com.theanimegroup.movie_ticket_booking_client.models.entity.ShowTime;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private MovieService movieService;
    private Button btnBook;

    private TextView titleTextView;
    private TextView directorTextView;
    private TextView statusTextView;
    private TextView dateStartTextView;
    private TextView dateEndTextView;
    private TextView descriptionTextView;
    private TextView showtimeTextView;
    private ImageView movieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_movie_detail); // Ensure you create this layout

        // Initialize views
        titleTextView = findViewById(R.id.info_movie_title);
        directorTextView = findViewById(R.id.info_movie_director);
        statusTextView = findViewById(R.id.info_movie_status);
        dateStartTextView = findViewById(R.id.info_movie_date_start);
        dateEndTextView = findViewById(R.id.info_movie_date_end);
        descriptionTextView = findViewById(R.id.info_movie_description);
        showtimeTextView = findViewById(R.id.info_movie_showtime);
        movieImageView = findViewById(R.id.info_movie_image);

        btnBook = findViewById(R.id.info_movie_button);

        movieService = RetrofitClient.getInstance().create(MovieService.class);
        loadMovieDetails();
        btnBook.setOnClickListener(v -> {
            int movieId = getIntent().getIntExtra("movieId", -1);
            if (movieId != -1) {
                // Create an intent to start ShowTimeActivity
                Intent intent = new Intent(MovieDetailActivity.this, ShowTimeActivity.class);
                intent.putExtra("movieId", movieId); // Pass the movie ID to the new activity
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, "Movie ID is invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMovieDetails() {
        int movieId = getIntent().getIntExtra("movieId", -1);
        Log.d("MovieDetailActivity", "Loading details for Movie ID: " + movieId);

        Call<ResponseObject<Movie>> call = movieService.getMovieDetails(1);
        call.enqueue(new Callback<ResponseObject<Movie>>() {
            @Override
            public void onResponse(Call<ResponseObject<Movie>> call, Response<ResponseObject<Movie>> response) {
                Log.d("MovieDetailActivity", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    ResponseObject<Movie> responseObject = response.body();
                        Movie movie = responseObject.getData();
                        titleTextView.setText(movie.getName());
                        directorTextView.setText(movie.getDirectorName());
                        statusTextView.setText(String.valueOf(movie.getStatus()));
                        dateStartTextView.setText(movie.getDateStart().toString());
                        dateEndTextView.setText(movie.getDateEnd().toString());
                        descriptionTextView.setText(movie.getDescription());
                        showtimeTextView.setText(movie.getShowtime().toString());
                        new LoadImageTask(movieImageView).execute(movie.getImage());
                        Log.d("MovieDetailActivity", "Movie details loaded successfully");
                    } else {
                        Log.e("MovieDetailActivity", "Movie data is null or status is false");
                    }

            }

            @Override
            public void onFailure(Call<ResponseObject<Movie>> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
