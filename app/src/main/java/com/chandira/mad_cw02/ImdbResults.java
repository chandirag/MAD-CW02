package com.chandira.mad_cw02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImdbResults extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    ListView listView;
    ArrayList<String> movies;
    ArrayList<String> movieIDs;
    ArrayList<String> ratings;

    TextView movieTitleColumnHeading, loadingTextView;
    ImageView imdbRatingColumnHeading;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb_results);

        query = getIntent().getStringExtra("query");

        movies = new ArrayList<>();
//        movies.add("Iron Man");
//        movies.add("The Avengers");

        ratings = new ArrayList<>();
//        ratings.add("8.8");
//        ratings.add("7.5");

        movieTitleColumnHeading = findViewById(R.id.textViewMovieTitleColumn);
        imdbRatingColumnHeading = findViewById(R.id.imdbColumn);
        loadingTextView = findViewById(R.id.loadingTextView);

        fetchData();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new MovieLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Convert the response to a JSON object.
            JSONObject jsonObject = new JSONObject(data);

            // Retrieve movie titles with descriptions
            JSONArray searchResultsArray = jsonObject.getJSONArray("results");
            int i = 0;
            String movieTitle = null;
            String rating;
            while (i < searchResultsArray.length() || movieTitle == null) {
                JSONObject movieItem = searchResultsArray.getJSONObject(i);
                try {
                    String title = movieItem.getString("title");
                    String description = movieItem.getString("description");
                    String id = movieItem.getString("id");
                    movieTitle = title + " " + description;
                    movies.add(movieTitle);
                    ratings.add(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }


            MovieListAdapter adapter = new MovieListAdapter(this, movies, ratings);
            listView = findViewById(R.id.listView);
            listView.setVisibility(View.INVISIBLE);

            if (movies.size() >= 0) {
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                movieTitleColumnHeading.setVisibility(View.VISIBLE);
                imdbRatingColumnHeading.setVisibility(View.VISIBLE);
                loadingTextView.setVisibility(View.INVISIBLE);
            } else {
                loadingTextView.setText("No Results Found!");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) { }

    public void fetchData() {
        String queryString = query;

        // Check the status of the network connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        // If the network is available & connected
        // start a MovieLoader AsyncTask
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            // TODO: Display loading message to user
            loadingTextView.setText("Loading...");
            imdbRatingColumnHeading.setVisibility(View.INVISIBLE);
            movieTitleColumnHeading.setVisibility(View.INVISIBLE);
        }

    }
}