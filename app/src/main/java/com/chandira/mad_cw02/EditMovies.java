package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class EditMovies extends AppCompatActivity {
    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> movies;
    ArrayList<String> sortedMovies;
    ArrayList<String> checkedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        initialize();
    }

    public void initialize() {
        listView = findViewById(R.id.editMoviesListView);
        movies = new ArrayList<>();
        sortedMovies = new ArrayList<>();
        checkedMovies = new ArrayList<>();

        db = new DBHelper(this);

        Cursor data = db.getData();

        // Iterate over all records and add the movie name to the ArrayList 'sortedMovies'
        while(data.moveToNext()) {
            String movieTitle = data.getString(1);
            sortedMovies.add(movieTitle);
        }
        Collections.sort(sortedMovies);

        // Set sorted movies list as the list view adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sortedMovies);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start 'Edit Movie Detail' activity
                Intent intent = new Intent(EditMovies.this, EditMovieDetails.class);
                intent.putExtra("listItem", (String) listView.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }
}