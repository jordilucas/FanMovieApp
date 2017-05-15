package com.sda.david.fanmovieapp.movie_genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.api.interfaces.MovieService;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.MovieTypeItem;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.util.MovieGenre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 29/04/2017.
 */

public class MovieGenreFragment extends Fragment {

    public static final String TAG = "MovieGenreFrag";
    private static final String ARG_USER = "arg_user";

    private User user;

    RecyclerView rvMoviesGenre;

    List<Movie> movies;

    public static MovieGenreFragment newInstance(User user) {
        MovieGenreFragment fragment = new MovieGenreFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Updating user after actions in MovieDetailAct
        //After put db, change this way do update
        user = BaseActivity.user;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_genre, container, false);
        initComponents(rootView);

        return rootView;
    }

    private void initComponents(View rootView) {
        rvMoviesGenre = (RecyclerView) rootView.findViewById(R.id.rv_movies_genre);
        rvMoviesGenre.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMoviesGenre.setLayoutManager(mLinearLayoutManager);

        findAllMovies();
    }

    private void updateAdapter(List<Movie> movies) {
        List<Long> genresIds = MovieGenre.getGenresIdList();

        List<MovieTypeItem> movieTypeItemList = new ArrayList<>();
        MovieTypeItem movieTypeItem;

        for (Long genreId : genresIds) {
            //Getting name of current genre
            String genreName = MovieGenre.getGenreNameById(genreId);

            //Getting list of movies by current genre
            List<Movie> moviesByGenre = getMoviesByGenre(movies, genreId);

            //Filling list of items to external recyclerView
            if (!moviesByGenre.isEmpty()) {
                movieTypeItem = new MovieTypeItem(genreName, MovieTypeItem.Type.TITLE);
                movieTypeItemList.add(movieTypeItem);
                movieTypeItem = new MovieTypeItem(moviesByGenre, MovieTypeItem.Type.CONTENT);
                movieTypeItemList.add(movieTypeItem);
            }
        }

        MovieGenreAdapter adapter = new MovieGenreAdapter(getContext(), movieTypeItemList);
        rvMoviesGenre.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<Movie> getMoviesByGenre(List<Movie> movies, Long genreId) {
        List<Movie> moviesByGenre = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getGenreIds().contains(genreId)) {
                moviesByGenre.add(movie);
            }
        }

        return moviesByGenre;
    }

    private void findAllMovies() {
        Call<List<Movie>> call = ServiceGenerator.createService(MovieService.class).findAll();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    movies = response.body();
                    updateAdapter(movies);
                } else {
                    ServiceGenerator.verifyErrorResponse(response.code(), rvMoviesGenre, getContext(), false, getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                ServiceGenerator.verifyFailedConnection(t, rvMoviesGenre, getContext());
            }
        });
    }

}
