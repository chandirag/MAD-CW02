package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Favourites extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> movies;
    ArrayList<String> sortedMovies;
    ArrayList<String> checkedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        initialize();
    }

    private void initialize() {
        listView = findViewById(R.id.favouritesList);
        movies = new ArrayList<>();
        sortedMovies = new ArrayList<>();
        checkedMovies = new ArrayList<>();

        db = new DBHelper(this);

        Cursor data = db.getFavourites();
        while(data.moveToNext()) {
            String movieTitle = data.getString(1);
            sortedMovies.add(movieTitle);
        }
        Collections.sort(sortedMovies);

        // Set sorted movies list as the list view adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, sortedMovies);
        listView.setAdapter(adapter);

        // Update checked status for each list item
        for(int i = 0; i < listView.getCount(); i++) {
            Cursor movie = db.getRecordWithTitle((String) listView.getItemAtPosition(i));
            int favouriteChecked = movie.getInt(7);
            if (favouriteChecked == 1) {
                listView.setItemChecked(i, true);
            } else if (favouriteChecked == 0) {
                listView.setItemChecked(i, false);
            }
        }
    }

    public void handleSaveChanges(View view) {
        // Update database according to checked status from list view
        for(int i = 0; i <listView.getCount(); i++) {
            if(listView.isItemChecked(i)) {
                Cursor checkedItem = db.getRecordWithTitle((String) listView.getItemAtPosition(i));
                int _id = checkedItem.getInt(0);
                db.updateIsFavouriteStatusOfMovie(_id, true);
            } else if (!listView.isItemChecked(i)) {
                Cursor uncheckedItem = db.getRecordWithTitle((String) listView.getItemAtPosition(i));
                int _id = uncheckedItem.getInt(0);
                db.updateIsFavouriteStatusOfMovie(_id, false);
            }
        }

        // Reset list view
        initialize();

        Toast.makeText(this, "Favourites Updated!", Toast.LENGTH_SHORT).show();
    }
}