package com.chandira.mad_cw02;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for IMDB API
    private static final String IMDB_BASE_URL = "https://imdb-api.com/en/API";
    // Endpoint: For searching movie titles
    private static final String SEARCH_ENDPOINT = "/SearchTitle";
    // Endpoint: For getting ratings of a given imdb movie id
    private static final String RATINGS_ENDPOINT = "/Ratings";
    // API KEY
    private static final String API_KEY = "k_ntsisa72";

    static String getMovieInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJSONString = null;

        try {

            String uriToBeParsed = IMDB_BASE_URL + SEARCH_ENDPOINT + "/" + API_KEY + "/" + queryString;
            Uri uri = Uri.parse(uriToBeParsed);
            URL requestURL = new URL(uri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the input stream
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if(builder.length() == 0) {
                // If stream is empty do not parse
                return null;
            }
            moviesJSONString = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, moviesJSONString);
        return moviesJSONString;
    }

    static String getRatingInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String ratingsJSONString = null;

        try {

            String uriToBeParsed = IMDB_BASE_URL + RATINGS_ENDPOINT + "/" + API_KEY + "/" + queryString;
            Uri uri = Uri.parse(uriToBeParsed);
            URL requestURL = new URL(uri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the input stream
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if(builder.length() == 0) {
                // If stream is empty do not parse
                return null;
            }
            ratingsJSONString = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, ratingsJSONString);
        return ratingsJSONString;
    }

}
