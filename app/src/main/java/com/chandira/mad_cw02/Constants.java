package com.chandira.mad_cw02;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

    public static final String MOVIE_TABLE_NAME = "MovieDetails";

    // Columns in the 'MovieDetails' Database
    public static final String MOVIE_TITLE = "movieTitle";
    public static final String YEAR_RELEASED = "movieYearReleased";
    public static final String DIRECTOR = "movieDirector";
    public static final String CAST = "movieCast";
    public static final String RATING = "movieRating";
    public static final String REVIEW = "movieReview";
    public static final String IS_FAVOURITE = "isFavourite";
}
