package com.chandira.mad_cw02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<String> movieImageUrls;
    String rating;

    TextView movieTitleColumnHeading, loadingTextView;
    ImageView imdbRatingColumnHeading;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb_results);

        query = getIntent().getStringExtra("query");

        movies = new ArrayList<>();
        ratings = new ArrayList<>();
        movieIDs = new ArrayList<>();
        movieImageUrls = new ArrayList<>();

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

            movies = new ArrayList<>();
            ratings = new ArrayList<>();
            movieIDs = new ArrayList<>();
            movieImageUrls = new ArrayList<>();

            // Convert the response to a JSON object.
            JSONObject jsonObject = new JSONObject(data);

            // Retrieve movie titles with descriptions
            JSONArray searchResultsArray = jsonObject.getJSONArray("results");
            int i = 0;
            String movieTitle = null;
//            String rating;
            while (i < searchResultsArray.length() || movieTitle == null) {
                JSONObject movieItem = searchResultsArray.getJSONObject(i);
                try {
                    String title = movieItem.getString("title");
                    String description = movieItem.getString("description");
                    String id = movieItem.getString("id");
                    String movieImageUrl = movieItem.getString("image");
                    movieTitle = title + " " + description;

//                    RatingResults ratingResults = new RatingResults(id);

                    movieIDs.add(id);
                    movies.add(movieTitle);
                    movieImageUrls.add(movieImageUrl);
                    ratings.add(rating);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }


            MovieListAdapter adapter = new MovieListAdapter(this, movies, ratings);
            listView = findViewById(R.id.listView);
            listView.setVisibility(View.INVISIBLE);

            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
            movieTitleColumnHeading.setVisibility(View.VISIBLE);
            imdbRatingColumnHeading.setVisibility(View.VISIBLE);
            loadingTextView.setVisibility(View.INVISIBLE);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), MovieImageViewActivity.class);
                    intent.putExtra("imageUrl", movieImageUrls.get(position));
                    intent.putExtra("movieTitle", movies.get(position));
                    startActivity(intent);
                }
            });



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

            // While data is loading: display message to user
            loadingTextView.setText("Loading...");
            imdbRatingColumnHeading.setVisibility(View.INVISIBLE);
            movieTitleColumnHeading.setVisibility(View.INVISIBLE);
        }
    }

//    class RatingResults implements LoaderManager.LoaderCallbacks<String> {
//        String movieID;
//
//        public RatingResults(String movieID) {
//            this.movieID = movieID;
//            fetchRatingsData(movieID);
//        }
//
//        @NonNull
//        @Override
//        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
//            String queryString = "";
//
//            if (args != null) {
//                queryString = args.getString("queryString");
//            }
//            return new RatingsLoader(getApplicationContext(), queryString);
//        }
//
//        @Override
//        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
//            try {
//                // Convert the response to a JSON object.
//                JSONObject jsonObject = new JSONObject(data);
//
//                // Get imdb rating
//                rating = jsonObject.getString("imDb");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onLoaderReset(@NonNull Loader<String> loader) {
//
//        }
//
//        public void fetchRatingsData(String queryID) {
//            // Check the status of the network connection
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = null;
//            if (connectivityManager != null) {
//                networkInfo = connectivityManager.getActiveNetworkInfo();
//            }
//
//            if (networkInfo != null && networkInfo.isConnected()) {
//                Bundle queryBundle = new Bundle();
//                queryBundle.putString("queryString", queryID);
//                getSupportLoaderManager().restartLoader(0, queryBundle, this);
//
//                // While data is loading: display message to user
//                loadingTextView.setText("Loading...");
//                imdbRatingColumnHeading.setVisibility(View.INVISIBLE);
//                movieTitleColumnHeading.setVisibility(View.INVISIBLE);
//            }
//        }
//    }

}