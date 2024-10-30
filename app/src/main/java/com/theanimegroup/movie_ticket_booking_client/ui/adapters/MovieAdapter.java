package com.theanimegroup.movie_ticket_booking_client.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.models.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }
    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
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



        Movie movie = movies.get(position);
        id.setText(movie.getId());
        name.setText(movie.getName());
        description.setText(movie.getDescription());
        image.setImageURI(Uri.parse(movie.getImage()));
        de.setText(movie.getDateStart().toString());
        ds.setText(movie.getDateEnd().toString());
        s.setText(movie.getStatus());
        dn.setText(movie.getDirectorName());
        ls.setText(movie.getShowtime().size());

        return convertView;
    }
}