package com.sda.david.fanmovieapp.movies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.api.interfaces.UserService;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.util.MovieGenre;
import com.sda.david.fanmovieapp.util.ShowMessageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 02/05/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailAct";

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

    private Menu menu;

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
                toogleFavorite();
            }
        });

        changeMenuFavoriteIcon();

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.menu = menu;
//        getMenuInflater().inflate(R.menu.main_right_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        changeMenuFavoriteIcon();
//        return super.onPrepareOptionsMenu(menu);
//    }

    private void changeMenuFavoriteIcon() {
//        MenuItem menuItem = menu.getItem(0);
        if(user.getFavoritesMovies().contains(movie.get_id()))
//            menuItem.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_favorite_black));
            fab.setImageResource(R.mipmap.ic_favorite_black);
        else
//            menuItem.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_favorite_border_black));
            fab.setImageResource(R.mipmap.ic_favorite_border_black);

    }

    private void toogleFavorite() {
        if(user.getFavoritesMovies().contains(movie.get_id())) {
            user.getFavoritesMovies().remove(movie.get_id());
            removeFavorite();
        } else {
            user.getFavoritesMovies().add(movie.get_id());
            addFavorite();
        }

        BaseActivity.user = user;

        changeMenuFavoriteIcon();

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_favorite) {
//            toogleFavorite();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void addFavorite() {
        Call<User> call = ServiceGenerator.createService(UserService.class).addFavorite(user.getId(), Arrays.asList(movie.get_id()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    ShowMessageUtil.longSnackBar(tvGenres, getString(R.string.favorite_added));
                } else {
                    addFavoriteOnError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                addFavoriteOnError();
            }
        });
    }

    private void addFavoriteOnError() {
        user.getFavoritesMovies().remove(movie.get_id());
        BaseActivity.user = user;
        changeMenuFavoriteIcon();
        ShowMessageUtil.longSnackBar(tvGenres, getString(R.string.favorite_not_added));
    }

    private void removeFavorite() {
        Call<User> call = ServiceGenerator.createService(UserService.class).removeFavorite(user.getId(), Arrays.asList(movie.get_id()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    ShowMessageUtil.longSnackBar(tvGenres, getString(R.string.favorite_removed));
                } else {
                    removeFavoriteOnError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                removeFavoriteOnError();
            }
        });
    }

    private void removeFavoriteOnError() {
        user.getFavoritesMovies().add(movie.get_id());
        BaseActivity.user = user;
        changeMenuFavoriteIcon();
        ShowMessageUtil.longSnackBar(tvGenres, getString(R.string.favorite_not_removed));
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
