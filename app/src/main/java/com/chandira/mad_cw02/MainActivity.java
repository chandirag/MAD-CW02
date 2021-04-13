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

    // OnClick Handler for 'Register Movie' button
    public void handleRegisterMovie(View view) {
        Intent intent = new Intent(this, RegisterMovie.class);
        startActivity(intent);
    }

    // OnClick Handler for 'Display Movies' button
    public void handleDisplayMovies(View view) {
        Intent intent = new Intent(this, DisplayMovies.class);
        startActivity(intent);
    }

    // OnClick Handler for 'Display Favourites' button
    public void handleDisplayFavourites(View view) {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    // OnClick Handler for 'Edit Movies' button
    public void handleEditMovies(View view) {
        Intent intent = new Intent(this, EditMovies.class);
        startActivity(intent);
    }

    // OnClick Handler for 'Search' button
    public void handleSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    // OnClick Handler for 'Ratings' button
    public void handleRatings(View view) {
        Intent intent = new Intent(this, Ratings.class);
        startActivity(intent);
    }
}