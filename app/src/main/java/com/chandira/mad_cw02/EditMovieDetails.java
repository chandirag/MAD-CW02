package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;

public class EditMovieDetails extends AppCompatActivity {
    TextInputLayout movieTitleTextInput, movieDirectorTextInput, movieCastTextInput, movieReviewTextInput;
    TextInputLayout movieYearReleasedTextInput, movieRatingTextInput;
    String movieTitle, director, cast, review, yearReleased, rating;
    int isFavourite, _id;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_details);

        // Set Activity Title as the Movie Name
        String itemName = getIntent().getStringExtra("listItem");
        setTitle(itemName);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        db = new DBHelper(this);

        // Title
        movieTitleTextInput = findViewById(R.id.txtInputLayoutMovieTitle_EditMovieDetails);
        movieTitleTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { movieTitle = s.toString(); }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { movieTitle = s.toString(); }
        });

        // Year Released
        movieYearReleasedTextInput = findViewById(R.id.txtInputLayoutYearReleased_EditMovieDetails);
        movieYearReleasedTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                yearReleased = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                yearReleased = s.toString();
            }
        });

        // Director
        movieDirectorTextInput = findViewById(R.id.txtInputLayoutDirector_EditMovieDetails);
        movieDirectorTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                director = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { director = s.toString(); }
        });

        // Cast
        movieCastTextInput = findViewById(R.id.txtInputLayoutCast_EditMovieDetails);
        movieCastTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { cast = s.toString(); }
        });

        // Review
        movieReviewTextInput = findViewById(R.id.txtInputLayoutReview_EditMovieDetails);
        movieReviewTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                review = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                review = s.toString();
            }
        });

        retrieveMovieData(itemName);
    }


    public void retrieveMovieData(String title) {
        Cursor data = db.getRecordWithTitle(title);

        // Get existing details and add them to text fields to be edited by the user
        _id = data.getInt(0);
        movieTitle = data.getString(1);
        yearReleased = data.getString(2);
        director = data.getString(3);
        cast = data.getString(4);
        rating = data.getString(5);
        review = data.getString(6);

        movieTitleTextInput.getEditText().setText(movieTitle);
        movieYearReleasedTextInput.getEditText().setText(yearReleased);
        movieDirectorTextInput.getEditText().setText(director);
        movieCastTextInput.getEditText().setText(cast);
        movieReviewTextInput.getEditText().setText(review);
    }

    public void handleUpdate(View view) {
        db.updateMovie(_id, movieTitle, yearReleased, director, cast, rating, review, isFavourite);
        finish();
    }
}