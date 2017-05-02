package com.sda.david.fanmovieapp.movies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;

/**
 * Created by david on 02/05/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "arg_movie";

    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getIntent() != null) {
            movie = getIntent().getParcelableExtra(ARG_MOVIE);
        }

        setupToolbar();
        initComponents();
        fillFieldsOnScreen();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initComponents() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO adicionar favorito
            }
        });
    }

    private void fillFieldsOnScreen() {
        if(movie != null) {
            mCollapsingToolbarLayout.setTitle(movie.getTitle());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
