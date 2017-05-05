package com.sda.david.fanmovieapp.movies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.util.MovieGenre;
import com.squareup.picasso.Picasso;

/**
 * Created by david on 02/05/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "arg_movie";
    public static final String ARG_USER = "arg_user";

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ImageView ivMoviePoster;
    TextView tvReleaseDate;
    TextView tvOriginalLanguage;
    TextView tvGenres;
    TextView tvMovieOriginalTitle;
    TextView tvMovieOverview;
    ImageView ivMovieStar1;
    ImageView ivMovieStar2;
    ImageView ivMovieStar3;
    ImageView ivMovieStar4;
    ImageView ivMovieStar5;
    FloatingActionButton fab;

    private Movie movie;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getIntent() != null) {
            movie = getIntent().getParcelableExtra(ARG_MOVIE);
            user = getIntent().getParcelableExtra(ARG_USER);
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
        tvGenres = (TextView) findViewById(R.id.tv_genres);
        tvMovieOriginalTitle = (TextView) findViewById(R.id.tv_movie_original_title);
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

            if(!movie.getGenreIds().isEmpty()) {
                String genres = "(";

                for(Long genreId : movie.getGenreIds()) {
                    genres += MovieGenre.getGenreNameById(genreId) + ", ";
                }
                genres = genres.substring(0, genres.length() - 2) + ")";
                tvGenres.setText(genres);
            } else {
                tvGenres.setVisibility(View.GONE);
            }

            verifyVote();

            if(!movie.getOriginalTitle().equals(""))
                tvMovieOriginalTitle.setText(movie.getOriginalTitle());
            else {
                tvMovieOriginalTitle.setText(getString(R.string.not_registered));
            }

            if(!movie.getOverview().equals(""))
                tvMovieOverview.setText(movie.getOverview());
            else {
                tvMovieOverview.setText(getString(R.string.not_registered));
            }

        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
