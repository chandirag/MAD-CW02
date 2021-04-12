package com.chandira.mad_cw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;

public class Search extends AppCompatActivity {

    DBHelper db;

    ListView listViewByTitle;
    ListView listViewByDirector;
    ListView listViewByCast;

    ArrayAdapter<String> adapterTitle;
    ArrayAdapter<String> adapterDirector;
    ArrayAdapter<String> adapterCast;

    ArrayList<String> moviesMatchingTitle;
    ArrayList<String> moviesMatchingDirector;
    ArrayList<String> moviesMatchingCast;

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    TextView titleSectionHeader, directorSectionHeader, castSectionHeader;

    TextInputLayout searchInput;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listViewByTitle = findViewById(R.id.searchMoviesListView_ByTitle);
        listViewByDirector = findViewById(R.id.searchMoviesListView_ByDirector);
        listViewByCast = findViewById(R.id.searchMoviesListView_ByCast);

        moviesMatchingTitle = new ArrayList<>();
        moviesMatchingDirector = new ArrayList<>();
        moviesMatchingCast = new ArrayList<>();

        titleSectionHeader = findViewById(R.id.textViewTitleSection);
        directorSectionHeader = findViewById(R.id.textViewDirectorSection);
        castSectionHeader = findViewById(R.id.textViewCastSection);

        linearLayout1 = findViewById(R.id.linearLayout1);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);

        db = new DBHelper(this);

        searchInput = findViewById(R.id.txtInputLayoutSearch);
        searchInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { searchQuery = s.toString(); }
        });

        initialize();
    }

    private void initialize() {
        // By Title
        adapterTitle = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moviesMatchingTitle);
        listViewByTitle.setAdapter(adapterTitle);
        listViewByTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Search.this, EditMovieDetails.class);
                intent.putExtra("listItem", (String) listViewByTitle.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        // By Director
        adapterDirector = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moviesMatchingDirector);
        listViewByDirector.setAdapter(adapterDirector);
        listViewByDirector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Search.this, EditMovieDetails.class);
                intent.putExtra("listItem", (String) listViewByDirector.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        // By Cast
        adapterCast = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moviesMatchingCast);
        listViewByCast.setAdapter(adapterCast);
        listViewByCast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Search.this, EditMovieDetails.class);
                intent.putExtra("listItem", (String) listViewByCast.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }


    public void handleSearchMovie(View view) {
        // Reset ArrayLists
        moviesMatchingTitle = new ArrayList<>();
        moviesMatchingDirector = new ArrayList<>();
        moviesMatchingCast = new ArrayList<>();

        titleSectionHeader.setVisibility(View.GONE);
        directorSectionHeader.setVisibility(View.GONE);
        castSectionHeader.setVisibility(View.GONE);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);

        // If text field is empty reset ListView
        if (searchQuery.trim().equalsIgnoreCase("")) {
            moviesMatchingTitle = new ArrayList<>();
            moviesMatchingDirector = new ArrayList<>();
            moviesMatchingCast = new ArrayList<>();
        } else {
            String query = "'%" + searchQuery + "%'";
            Cursor dataWithMatchingTitle = db.searchMoviesWithTitleLike(query);
            Cursor dataWithMatchingDirector = db.searchMoviesWithDirectorLike(query);
            Cursor dataWithMatchingCast = db.searchMoviesWithCastLike(query);

            // If there are matching data with the titles in the database
            if (dataWithMatchingTitle.getCount() != 0) {
                while(dataWithMatchingTitle.moveToNext()) {
                    moviesMatchingTitle.add(dataWithMatchingTitle.getString(1));
                }
                Collections.sort(moviesMatchingTitle);
                titleSectionHeader.setText("Results with '" + searchQuery + "' in the title:");
                titleSectionHeader.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.VISIBLE);
            }


            // If there are matching data with the director in the database
            if (dataWithMatchingDirector.getCount() != 0) {
                while(dataWithMatchingDirector.moveToNext()) {
                    moviesMatchingDirector.add(dataWithMatchingDirector.getString(1));
                }
                Collections.sort(moviesMatchingDirector);
                directorSectionHeader.setText("Results with '" + searchQuery + "' in the Director field:");
                directorSectionHeader.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
            }

            // If there are matching data with the cast in the database
            if (dataWithMatchingCast.getCount() != 0) {
                while(dataWithMatchingCast.moveToNext()) {
                    moviesMatchingCast.add(dataWithMatchingCast.getString(1));
                }
                Collections.sort(moviesMatchingCast);
                castSectionHeader.setText("Results with '" + searchQuery + "' in the Cast:");
                castSectionHeader.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
            }
        }
        initialize();
    }
}