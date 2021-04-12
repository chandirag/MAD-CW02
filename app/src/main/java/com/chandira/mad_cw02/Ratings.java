package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Ratings extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> movies;

    String selectedMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        listView = findViewById(R.id.listView);
        movies = new ArrayList<>();

        db = new DBHelper(this);
        retrieveMovieData();
    }

    private void retrieveMovieData() {
        Cursor data = db.getData();

        // Iterate over all records and add the movie name to the ArrayList 'sortedMovies'
        while(data.moveToNext()) {
            String movieTitle = data.getString(1);
            movies.add(movieTitle);
        }
        Collections.sort(movies);

        // Set sorted movies list as the list view adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, movies);
        listView.setAdapter(adapter);

        if (data.getCount() != 0) {
            listView.setItemChecked(0, true);
            selectedMovieTitle = (String) listView.getItemAtPosition(0);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMovieTitle = ((TextView) view).getText().toString();
            }
        });
    }


    public void handleImdbLookup(View view) {
        Intent intent = new Intent(Ratings.this, ImdbResults.class);
        intent.putExtra("query", selectedMovieTitle);
        startActivity(intent);
    }
}