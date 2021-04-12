package com.chandira.mad_cw02;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MovieLoader extends AsyncTaskLoader<String> {

    private String queryString;

    public MovieLoader(@NonNull Context context, String queryString) {
        super(context);
        this.queryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getMovieInfo(queryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
