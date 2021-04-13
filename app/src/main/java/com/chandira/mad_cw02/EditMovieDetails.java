package com.chandira.mad_cw02;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class EditMovieDetails extends AppCompatActivity {
    TextInputLayout movieTitleTextInput, movieDirectorTextInput, movieCastTextInput, movieReviewTextInput;
    TextInputLayout movieYearReleasedTextInput;
    String movieTitle, director, cast, review, yearReleased, rating;
    int isFavourite, _id;
    CheckBox isFavouriteCheckBox;
    RatingBar ratingBar;
    DBHelper db;
    int MIN_YEAR = Constants.MIN_YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_details);

        // Set Activity Title as the Movie Name
        String itemName = getIntent().getStringExtra("listItem");
        setTitle(itemName);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        db = new DBHelper(this);
        ratingBar = findViewById(R.id.ratingBar);
        isFavouriteCheckBox = findViewById(R.id.isFavouriteCheckBox);

        // Initializing Material Input Field for: Title
        movieTitleTextInput = findViewById(R.id.txtInputLayoutMovieTitle_EditMovieDetails);
        movieTitleTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                movieTitle = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                movieTitle = s.toString();
            }
        });

        // Initializing Material Input Field for: Year Released
        movieYearReleasedTextInput = findViewById(R.id.txtInputLayoutYearReleased_EditMovieDetails);
        movieYearReleasedTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                yearReleased = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                yearReleased = s.toString();
            }
        });

        // Initializing Material Input Field for: Director
        movieDirectorTextInput = findViewById(R.id.txtInputLayoutDirector_EditMovieDetails);
        movieDirectorTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                director = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                director = s.toString();
            }
        });

        // Initializing Material Input Field for: Cast
        movieCastTextInput = findViewById(R.id.txtInputLayoutCast_EditMovieDetails);
        movieCastTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                cast = s.toString();
            }
        });

        // Initializing Material Input Field for: Review
        movieReviewTextInput = findViewById(R.id.txtInputLayoutReview_EditMovieDetails);
        movieReviewTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                review = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
        isFavourite = data.getInt(7);

        movieTitleTextInput.getEditText().setText(movieTitle);
        movieYearReleasedTextInput.getEditText().setText(yearReleased);
        movieDirectorTextInput.getEditText().setText(director);
        movieCastTextInput.getEditText().setText(cast);
        movieReviewTextInput.getEditText().setText(review);
        ratingBar.setRating(Float.parseFloat(rating));
        if (isFavourite == 1) {
            isFavouriteCheckBox.setChecked(true);
        } else if (isFavourite == 0) {
            isFavouriteCheckBox.setChecked(false);
        }
    }

    // OnClick Handler for 'Update Movie' button
    public void handleUpdate(View view) {
        if (isValid()) {
            int ratingInt = (int) ratingBar.getRating();
            rating = String.valueOf(ratingInt);
            if (isFavouriteCheckBox.isChecked()) {
                isFavourite = 1;
            } else if (!isFavouriteCheckBox.isChecked()) {
                isFavourite = 0;
            }
            db.updateMovie(_id, movieTitle, yearReleased, director, cast, rating, review, isFavourite);
            finish();
        }
    }

    // Input Validation
    private boolean isValid() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        boolean isTitleValid, isYearValid = true;

        if (movieTitleTextInput.getEditText().getText().toString().isEmpty()) {
            movieTitleTextInput.setError("Movie title is required.");
            isTitleValid = false;
        } else {
            movieTitleTextInput.setErrorEnabled(false);
            isTitleValid = true;
        }

        if (movieYearReleasedTextInput.getEditText().getText().toString().isEmpty()) {
            movieYearReleasedTextInput.setError("Year released is required.");
            isYearValid = false;
        } else if (Integer.parseInt(movieYearReleasedTextInput.getEditText().getText().toString()) < MIN_YEAR) {
            movieYearReleasedTextInput.setError("The oldest year you can enter is: " + MIN_YEAR);
            isYearValid = false;
        } else if (Integer.parseInt(movieYearReleasedTextInput.getEditText().getText().toString()) > currentYear) {
            movieYearReleasedTextInput.setError("The latest year you can enter is: " + currentYear);
            isYearValid = false;
        } else {
            movieYearReleasedTextInput.setErrorEnabled(false);
        }

        return (isTitleValid && isYearValid);
    }
}