package com.chandira.mad_cw02;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.chandira.mad_cw02.Constants.CAST;
import static com.chandira.mad_cw02.Constants.DIRECTOR;
import static com.chandira.mad_cw02.Constants.IS_FAVOURITE;
import static com.chandira.mad_cw02.Constants.MOVIE_TABLE_NAME;
import static com.chandira.mad_cw02.Constants.MOVIE_TITLE;
import static com.chandira.mad_cw02.Constants.RATING;
import static com.chandira.mad_cw02.Constants.REVIEW;
import static com.chandira.mad_cw02.Constants.YEAR_RELEASED;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Movies.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MOVIE_TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MOVIE_TITLE + " TEXT, "
                        + YEAR_RELEASED + " TEXT, "
                        + DIRECTOR + " TEXT, "
                        + CAST + " TEXT, "
                        + RATING + " TEXT, "
                        + REVIEW + " TEXT, "
                        + IS_FAVOURITE + " INTEGER"
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertMovie(String movieTitle, String movieYearReleased, String movieDirector,
                               String movieCast, String movieRating, String movieReview, int isFavourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_TITLE, movieTitle);
        contentValues.put(YEAR_RELEASED, movieYearReleased);
        contentValues.put(DIRECTOR, movieDirector);
        contentValues.put(CAST, movieCast);
        contentValues.put(RATING, movieRating);
        contentValues.put(REVIEW, movieReview);
        contentValues.put(IS_FAVOURITE, isFavourite);
        db.insert(MOVIE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MOVIE_TABLE_NAME, null);
        return cursor;
    }

}