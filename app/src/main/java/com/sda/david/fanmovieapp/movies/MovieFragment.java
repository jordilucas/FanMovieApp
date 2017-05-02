package com.sda.david.fanmovieapp.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.MovieService;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 29/04/2017.
 */

public class MovieFragment extends Fragment {

    public static final String TAG = "MovieFrag";
    private static final String ARG_1 = "arg_1";

    private boolean arg1;

    RecyclerView rvMovies;

    List<Movie> movies;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        initComponents(rootView);

        return rootView;
    }

    private void initComponents(View rootView) {
        rvMovies = (RecyclerView) rootView.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMovies.setLayoutManager(mLinearLayoutManager);

        findAllMovies();

    }

    private void fillScreen() {

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
                    MovieAdapter adapter = new MovieAdapter(getContext(), movies, movieClickListener());
                    rvMovies.setAdapter(adapter);
                    fillScreen();
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
