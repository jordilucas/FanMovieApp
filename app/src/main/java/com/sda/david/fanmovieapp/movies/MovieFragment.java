package com.sda.david.fanmovieapp.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.interfaces.MovieService;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.model.Movie;
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

public class MovieFragment extends Fragment {

    public static final String TAG = "MovieFrag";
    private static final String ARG_USER = "arg_user";

    private User user;

    SearchView svMovie;
    Spinner spinner;
    RecyclerView rvMovies;

    List<Movie> movies;
    List<String> genresList;

    public static MovieFragment newInstance(User user) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
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
        svMovie = (SearchView) rootView.findViewById(R.id.sv_movie);
        svMovie.setQueryHint(getString(R.string.search_the_movie));
        svMovie.setIconifiedByDefault(false);
        svMovie.setOnQueryTextListener(onQueryTextListener());
        spinner = (Spinner) rootView.findViewById(R.id.spinner_genre_movie);
        rvMovies = (RecyclerView) rootView.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
//        final CarouselLayoutManager mLinearLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
//        mLinearLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMovies.setLayoutManager(mLinearLayoutManager);

        genresList = MovieGenre.getGenresNamesLst();
        genresList.add(0, "Gênero");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, genresList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position != 0)
                    Log.d(TAG, "onItemSelected: " + genresList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findAllMovies();

    }

    private SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateAdapter(returnSearchMovieList(query));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateAdapter(returnSearchMovieList(newText));

                return false;
            }
        };
    }

    private List<Movie> returnSearchMovieList(String queryText) {
        List<Movie> listToSearch = new ArrayList<>();
        for(Movie movie : movies) {
            if(movie.getTitle().toLowerCase().contains(queryText.toLowerCase())
                    || movie.getOriginalTitle().toLowerCase().contains(queryText.toLowerCase())
                    && !listToSearch.contains(movie)) {
                listToSearch.add(movie);
            }
        }

        return listToSearch;
    }

    private void updateAdapter(List<Movie> movies) {
        MovieAdapter adapter = new MovieAdapter(getContext(), movies, movieClickListener());
        rvMovies.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener movieClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();

                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ARG_MOVIE, movies.get(position));
                intent.putExtra(MovieDetailActivity.ARG_USER, user);
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
