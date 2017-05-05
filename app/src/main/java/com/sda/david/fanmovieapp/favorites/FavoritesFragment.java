package com.sda.david.fanmovieapp.favorites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.api.interfaces.UserService;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.movies.MovieAdapter;
import com.sda.david.fanmovieapp.movies.MovieDetailActivity;
import com.sda.david.fanmovieapp.util.ShowMessageUtil;

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
    private static final String ARG_USER = "arg_user";

    RecyclerView rvMovies;
    private ProgressDialog dialog;

    private User user;
    List<Movie> movies;

    public static FavoritesFragment newInstance(User user) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        rvMovies = (RecyclerView) rootView.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMovies.setLayoutManager(mLinearLayoutManager);

        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Updating user after actions in MovieDetailAct
        //After put db, change this way do update
        user = BaseActivity.user;
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

                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ARG_MOVIE, movies.get(position));
                intent.putExtra(MovieDetailActivity.ARG_USER, user);
                startActivity(intent);
            }
        };
    }

    private void userListFavorites() {
        dialog.setMessage(getString(R.string.loading_favorites));
        dialog.show();
        Call<List<Movie>> call = ServiceGenerator.createService(UserService.class).findFavorites(user.getId());
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                dialog.dismiss();
                if(response.isSuccessful()) {
                    movies = response.body();
                    movies.removeAll(Collections.<Movie>singleton(null));
                    fillScreen();
                } else {
                    ShowMessageUtil.longSnackBar(rvMovies, getString(R.string.something_went_wrong));
                }

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                dialog.dismiss();
                ShowMessageUtil.longSnackBar(rvMovies, getString(R.string.something_went_wrong));
            }
        });
    }

}
