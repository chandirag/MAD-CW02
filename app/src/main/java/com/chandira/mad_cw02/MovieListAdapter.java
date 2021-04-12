package com.chandira.mad_cw02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> movieTitles;
    private final List<String> ratings;


    public MovieListAdapter(Activity context, List<String> movieTitles, List<String> ratings) {
        super(context, R.layout.list_item, movieTitles);
        this.context = context;
        this.movieTitles = movieTitles;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listItem = inflater.inflate(R.layout.list_item, null, true);

        TextView txtViewMovieTitle = (TextView) listItem.findViewById(R.id.movieTitle);
        TextView txtViewRating = (TextView) listItem.findViewById(R.id.rating);

        txtViewMovieTitle.setText(movieTitles.get(position));
        txtViewRating.setText(ratings.get(position));

        return listItem;
    }
}
