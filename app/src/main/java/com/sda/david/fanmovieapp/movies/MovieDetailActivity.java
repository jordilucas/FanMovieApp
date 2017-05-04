package com.sda.david.fanmovieapp.movies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by david on 02/05/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "arg_movie";

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ImageView ivMoviePoster;
    TextView tvMovieOverview;

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
        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_backdrop);
        tvMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
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

            String urlMoviePoster = "!";

            if(movie.getBackdropPath() != null)
                urlMoviePoster = movie.getBackdropPath();

            if(!urlMoviePoster.isEmpty() && urlMoviePoster.charAt(0) == '/')
                urlMoviePoster = "http://image.tmdb.org/t/p/w500" + urlMoviePoster;

            Picasso
                    .with(this)
                    .load(urlMoviePoster)
                    .into(ivMoviePoster);

            tvMovieOverview.setText(movie.getOverview());

        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
