package com.chandira.mad_cw02;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieImageViewActivity extends AppCompatActivity {
    String movieTitle;
    String movieImageUrl;

    TextView title;
    ImageView imageView;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image_view);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        movieImageUrl = getIntent().getStringExtra("imageUrl");
        movieTitle = getIntent().getStringExtra("movieTitle");

        setTitle(movieTitle);

        title = findViewById(R.id.selectedMovieTitle);
        title.setText(movieTitle);
        imageView = findViewById(R.id.movieImage);

        if (!movieImageUrl.trim().equalsIgnoreCase(""))
            imageLoader = (ImageLoader) new ImageLoader(imageView).execute(movieImageUrl);
        else {
            imageView.setVisibility(View.INVISIBLE);
            title.setText("Image does not exist!");
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true; //this does the trick
        }
        return super.onOptionsItemSelected(item);
    }
}