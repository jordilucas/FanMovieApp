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
    TextView tvReleaseDate;
    TextView tvOriginalLanguage;
    TextView tvTitleMovieOriginalTitle;
    TextView tvMovieOriginalTitle;
    TextView tvTitleMovieOverview;
    TextView tvMovieOverview;
    ImageView ivMovieStar1;
    ImageView ivMovieStar2;
    ImageView ivMovieStar3;
    ImageView ivMovieStar4;
    ImageView ivMovieStar5;
    FloatingActionButton fab;

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
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOriginalLanguage = (TextView) findViewById(R.id.tv_original_language);
        tvTitleMovieOriginalTitle = (TextView) findViewById(R.id.tv_title_movie_original_title);
        tvMovieOriginalTitle = (TextView) findViewById(R.id.tv_movie_original_title);
        tvTitleMovieOverview = (TextView) findViewById(R.id.tv_title_movie_overview);
        tvMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        ivMovieStar1 = (ImageView) findViewById(R.id.iv_movie_star1);
        ivMovieStar2 = (ImageView) findViewById(R.id.iv_movie_star2);
        ivMovieStar3 = (ImageView) findViewById(R.id.iv_movie_star3);
        ivMovieStar4 = (ImageView) findViewById(R.id.iv_movie_star4);
        ivMovieStar5 = (ImageView) findViewById(R.id.iv_movie_star5);

        fab = (FloatingActionButton) findViewById(R.id.fab);
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

            if(movie.getReleaseDate() != null) {
                String []realeaseDateSplited = movie.getReleaseDate().split("-");
                if(realeaseDateSplited.length > 0)
                    tvReleaseDate.setText(realeaseDateSplited[0]);
            }else
                tvReleaseDate.setVisibility(View.GONE);

            if(movie.getOriginalLanguage() != null)
                tvOriginalLanguage.setText(movie.getOriginalLanguage());
            else
                tvOriginalLanguage.setVisibility(View.GONE);

            verifyVote();

            if(movie.getOriginalTitle() != null)
                tvMovieOriginalTitle.setText(movie.getOriginalTitle());
            else {
                tvTitleMovieOriginalTitle.setVisibility(View.GONE);
                tvMovieOriginalTitle.setVisibility(View.GONE);
            }

            if(movie.getOverview() != null)
                tvMovieOverview.setText(movie.getOverview());
            else {
                tvTitleMovieOverview.setVisibility(View.GONE);
                tvMovieOverview.setVisibility(View.GONE);
            }

        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void verifyVote() {
        Float note = movie.getVoteAverage();

        if(note == 0) {
            fillStarsVote(
                R.mipmap.ic_star_border_black_18dp,
                R.mipmap.ic_star_border_black_18dp,
                R.mipmap.ic_star_border_black_18dp,
                R.mipmap.ic_star_border_black_18dp,
                R.mipmap.ic_star_border_black_18dp
            );
        } else if(0 < note && note <= 1) {
            fillStarsVote(
                    R.mipmap.ic_star_half_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(1 < note && note <= 2) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(2 < note && note <= 3) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_half_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(3 < note && note <= 4) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(4 < note && note <= 5) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_half_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(5 < note && note <= 6) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_border_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(6 < note && note <= 7) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_half_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(7 < note && note <= 8) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(8 < note && note <= 9) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_half_black_18dp,
                    R.mipmap.ic_star_border_black_18dp
            );
        } else if(9 < note && note <= 10) {
            fillStarsVote(
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp,
                    R.mipmap.ic_star_black_18dp
            );
        }
    }

    private void fillStarsVote(int imageStar1, int imageStar2, int imageStar3, int imageStar4, int imageStar5) {
        ivMovieStar1.setImageResource(imageStar1);
        ivMovieStar2.setImageResource(imageStar2);
        ivMovieStar3.setImageResource(imageStar3);
        ivMovieStar4.setImageResource(imageStar4);
        ivMovieStar5.setImageResource(imageStar5);
    }

}
