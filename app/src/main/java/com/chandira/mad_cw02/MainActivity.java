package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleRegisterMovie(View view) {
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    public void handleDisplayMovies(View view) {
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    public void handleDisplayFavourites(View view) {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    public void handleEditMovies(View view) {
        Intent intent = new Intent(this, EditMovies.class);
        startActivity(intent);
    }

    public void handleSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void handleRatings(View view) {
        Intent intent = new Intent(this, Ratings.class);
        startActivity(intent);
    }
}