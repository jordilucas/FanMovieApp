package com.sda.david.fanmovieapp.favorites;

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
import com.sda.david.fanmovieapp.api.UserService;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.movies.MovieAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 29/04/2017.
 */

public class FavoritesFragment extends Fragment {

    public static final String TAG = "FavoriteFrag";
    private static final String ARG_1 = "arg_1";

    private boolean arg1;

    RecyclerView rvMovies;

    List<Movie> movies;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
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

        userListFavorites();

    }

    private void fillScreen() {
        MovieAdapter adapter = new MovieAdapter(getContext(), movies, movieClickListener());
        rvMovies.setAdapter(adapter);
    }

    private View.OnClickListener movieClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                Log.d(TAG, "onClick: " + position);
            }
        };
    }

    private void userListFavorites() {
        Call<List<Movie>> call = ServiceGenerator.createService(UserService.class).findFavorites((long) 44);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(response.isSuccessful()) {
                    movies = response.body();
                    movies.removeAll(Collections.<Movie>singleton(null));
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
