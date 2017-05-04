package com.sda.david.fanmovieapp.movie_genre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.api.interfaces.MovieService;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.movies.MovieAdapter;
import com.sda.david.fanmovieapp.movies.MovieDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 29/04/2017.
 */

public class MovieGenreFragment extends Fragment {

    public static final String TAG = "MovieGenreFrag";
    private static final String ARG_1 = "arg_1";

    private boolean arg1;

    RecyclerView rvMoviesGenre;

    List<Movie> movies;

    public static MovieGenreFragment newInstance() {
        MovieGenreFragment fragment = new MovieGenreFragment();
        Bundle bundle = new Bundle();
        //Put arguments
        boolean arg1 = true;
        bundle.putBoolean(ARG_1, arg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            arg1 = getArguments().getBoolean(ARG_1);
        }
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
        MovieGenreAdapter adapter = new MovieGenreAdapter(getContext(), movies, movieClickListener());
        rvMoviesGenre.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener movieClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();

                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ARG_MOVIE, movies.get(position));
                startActivity(intent);

            }
        };
    }

    private void findAllMovies() {
        Call<List<Movie>> call = ServiceGenerator.createService(MovieService.class).findAll();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(response.isSuccessful()) {
                    movies = response.body();
                    updateAdapter(movies);
                } else {
                    Log.d(TAG, "onError: ");
                }

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}