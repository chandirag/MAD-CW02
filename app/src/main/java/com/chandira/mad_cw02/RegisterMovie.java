package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterMovie extends AppCompatActivity {
    TextInputLayout movieTitleTextInput, movieDirectorTextInput, movieCastTextInput, movieReviewTextInput;
    TextInputLayout movieYearReleasedTextInput, movieRatingTextInput;
    String movieTitle, director, cast, review, yearReleased, rating;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        db = new DBHelper(this);

        // Title
        movieTitleTextInput = findViewById(R.id.txtInputLayoutSearch);
        movieTitleTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { movieTitle = s.toString(); }
        });

        // Year Released
        movieYearReleasedTextInput = findViewById(R.id.txtInputLayoutYearReleased);
        movieYearReleasedTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                yearReleased = s.toString();
            }
        });

        // Director
        movieDirectorTextInput = findViewById(R.id.txtInputLayoutDirector);
        movieDirectorTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { director = s.toString(); }
        });

        // Cast
        movieCastTextInput = findViewById(R.id.txtInputLayoutCast);
        movieCastTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { cast = s.toString(); }
        });

        // Rating
        movieRatingTextInput = findViewById(R.id.txtInputLayoutRating);
        movieRatingTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                rating = s.toString();
            }
        });

        // Review
        movieReviewTextInput = findViewById(R.id.txtInputLayoutReview);
        movieReviewTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                review = s.toString();
            }
        });
    }

    public void handleAddMovie(View view) {
        db.insertMovie(movieTitle, yearReleased, director, cast, rating, review, 0);

        Toast toast = Toast.makeText(this, movieTitle + " added to database!", Toast.LENGTH_SHORT);
        toast.show();

        emptyTextFields(movieTitleTextInput, movieDirectorTextInput, movieCastTextInput,
                movieReviewTextInput, movieYearReleasedTextInput, movieRatingTextInput);
    }


    private static void emptyStringValues(String... strings) {
        for(String string: strings)
            string = "";
    }

    private static void emptyTextFields(TextInputLayout... textInputLayouts) {
        for(TextInputLayout textInputLayout: textInputLayouts)
            textInputLayout.getEditText().setText("");
    }
}