package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;
import com.theanimegroup.movie_ticket_booking_client.ui.activities.MovieActivity;
import com.theanimegroup.movie_ticket_booking_client.ui.activities.MovieDetailActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MovieAdapter extends BaseListAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_movie, parent, false);
        }

        TextView id = convertView.findViewById(R.id.movie_id);

        TextView name = convertView.findViewById(R.id.movie_title);
        ImageView image = convertView.findViewById(R.id.movie_image);
        TextView description = convertView.findViewById(R.id.movie_description);
        TextView ds = convertView.findViewById(R.id.movie_date_start);
        TextView de = convertView.findViewById(R.id.movie_date_end);
        TextView s = convertView.findViewById(R.id.movie_status);
        TextView dn = convertView.findViewById(R.id.movie_director_name);
        TextView ls = convertView.findViewById(R.id.movie_list);
        Button moreBtn = convertView.findViewById(R.id.more_button);

        Movie movie = items.get(position);
        id.setText(String.valueOf(movie.getId()));
        name.setText(movie.getName());
        description.setText(movie.getDescription());
        new MovieDetailActivity.LoadImageTask(image).execute(movie.getImage());

        image.setImageURI(Uri.parse(movie.getImage()));
        de.setText(movie.getDateStart().toString());
        ds.setText(movie.getDateEnd().toString());
        s.setText(movie.getStatus() == 1 ? "Valid" : "Not Valid");
        dn.setText(movie.getDirectorName());
        ls.setText(String.valueOf(movie.getShowtime().size()));

        moreBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movieId", String.valueOf(movie.getId()));
            context.startActivity(intent);
        });

        return convertView;
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
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