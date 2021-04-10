package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);
    }

    public void handleAddMovie(View view) {
        Toast toast = Toast.makeText(this, "Movie added to database!", Toast.LENGTH_SHORT);
        toast.show();
    }
}