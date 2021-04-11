package com.chandira.mad_cw02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> movies;
    ArrayList<Boolean> movieFavouriteStatus;
    String[] strings = {"Moon", "Saturn", "Earth", "Apple", "Banana", "Movie1", "Movie2", "Movie3",
            "Movie4", "Movie1", "Movie2", "Movie3", "Movie4", "Movie1", "Movie2", "Movie3", "Movie4", "Movie1", "Movie2", "Movie3", "Movie4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        listView = findViewById(R.id.listView);
        movies = new ArrayList<>();

        db = new DBHelper(this);
        retrieveMovieData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, movies);
        listView.setAdapter(adapter);
    }

    public void retrieveMovieData() {
        Cursor data = db.getData();
        if (data.getCount() == 0) {
            System.out.println("Error!");
        } else {
            while(data.moveToNext()) {
                String movieTitle = data.getString(1);
                movies.add(movieTitle);

            }
            Collections.sort(movies);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}