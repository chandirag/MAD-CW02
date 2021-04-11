package com.chandira.mad_cw02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> movies;
    ArrayList<String> sortedMovies;
    ArrayList<String> checkedMovies;
    ArrayList<Boolean> movieFavouriteStatus;
    String[] strings = {"Moon", "Saturn", "Earth", "Apple", "Banana", "Movie1", "Movie2", "Movie3",
            "Movie4", "Movie1", "Movie2", "Movie3", "Movie4", "Movie1", "Movie2", "Movie3", "Movie4", "Movie1", "Movie2", "Movie3", "Movie4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        listView = findViewById(R.id.listView);
        movies = new ArrayList<>();
        sortedMovies = new ArrayList<>();
        checkedMovies = new ArrayList<>();

        db = new DBHelper(this);
        retrieveMovieData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, sortedMovies);
        listView.setAdapter(adapter);
    }

    public void retrieveMovieData() {
        Cursor data = db.getData();

        while(data.moveToNext()) {
            String movieTitle = data.getString(1);
            movies.add(movieTitle);
            sortedMovies.add(movieTitle);
        }
        Collections.sort(sortedMovies);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void handleAddToFavourite(View view) {
        String itemSelected = "Selected items: \n";

        for(int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                checkedMovies.add((String) listView.getItemAtPosition(i));
                itemSelected += listView.getItemAtPosition(i) + "\n";
            }
        }

        for(String selectedItem: checkedMovies) {
//            Cursor selectedRecord = db.getRecordWithTitle(selectedItem);
//            if (selectedRecord.getCount() != 0) {
//                String databaseIDOfSelectedItem = String.valueOf(selectedRecord.getString(0));
//                db.updateIsFavouriteStatusOfMovie(Integer.parseInt(databaseIDOfSelectedItem), true);
//            }
            int databaseIdOfSelectedItem = db.getRecordWithTitle(selectedItem);
            if (databaseIdOfSelectedItem == -1)
                System.out.println("Some shit isn't working");
            else
                db.updateIsFavouriteStatusOfMovie(databaseIdOfSelectedItem, true);
        }

        Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
    }
}