package com.chandira.mad_cw02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> sortedMovies;
    ImageView imageView;
    TextView helperText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        listView = findViewById(R.id.listView);
        sortedMovies = new ArrayList<>();

        imageView = findViewById(R.id.helperImage);
        helperText = findViewById(R.id.textViewEmptyRecords);

        imageView.setVisibility(View.INVISIBLE);
        helperText.setVisibility(View.INVISIBLE);

        db = new DBHelper(this);
        retrieveMovieData();
    }

    public void retrieveMovieData() {
        Cursor data = db.getData();



        // Iterate over all records and add the movie name to the ArrayList 'sortedMovies'
        while(data.moveToNext()) {
            String movieTitle = data.getString(1);
            sortedMovies.add(movieTitle);
        }
        Collections.sort(sortedMovies);

        // Set sorted movies list as the list view adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, sortedMovies);
        listView.setAdapter(adapter);

        for(int i = 0; i < listView.getCount(); i++) {
            Cursor movie = db.getRecordWithTitle((String) listView.getItemAtPosition(i));
            int favouriteChecked = movie.getInt(7);
            if (favouriteChecked == 1) {
                listView.setItemChecked(i, true);
            } else if (favouriteChecked == 0) {
                listView.setItemChecked(i, false);
            }
        }

        if (data.getCount() == 0) {
            imageView.setVisibility(View.VISIBLE);
            helperText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void handleAddToFavourite(View view) {
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

        Toast.makeText(this, "Favourites Updated!", Toast.LENGTH_SHORT).show();
    }
}