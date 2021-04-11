package com.chandira.mad_cw02;

public class Movie {
    private int movieID;
    private String title;
    private int yearReleased;
    private String director;
    private String cast;
    private int rating;
    private String review;
    private boolean isFavourite;

    public Movie(int movieID, String title, int yearReleased, String director, String cast, int rating, String review, boolean isFavourite) {
        this.movieID = movieID;
        this.title = title;
        this.yearReleased = yearReleased;
        this.director = director;
        this.cast = cast;
        this.rating = rating;
        this.review = review;
        this.isFavourite = isFavourite;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
