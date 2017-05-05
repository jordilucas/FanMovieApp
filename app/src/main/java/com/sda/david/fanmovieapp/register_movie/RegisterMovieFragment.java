package com.sda.david.fanmovieapp.register_movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.util.MovieGenre;

import java.util.List;

/**
 * Created by david on 05/05/2017.
 */

public class RegisterMovieFragment extends Fragment {

    public static final String TAG = "RegMovieFrag";
    private static final String ARG_1 = "arg_1";

    EditText etMovieTitle;
    EditText etMovieOriginalTitle;
    EditText etMoviePoster;
    EditText etMovieReleaseDate;
    EditText etMovieOverview;
    EditText etMovieOriginalLanguage;
    EditText etMovieBackDrop;
    RatingBar rbMovieVoteAverage;
    TextView tvMovieGenres;
    Spinner spinGenreMovie;
    Button btAddGenre;

    private List<String> genresList;
    private boolean arg1;

    public static RegisterMovieFragment newInstance() {
        RegisterMovieFragment fragment = new RegisterMovieFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_register_movie, container, false);
        initComponents(rootView);

        return rootView;
    }

    private void initComponents(View rootView) {

        etMovieTitle = (EditText) rootView.findViewById(R.id.et_movie_title);
        etMovieOriginalTitle = (EditText) rootView.findViewById(R.id.et_movie_original_title);
        etMoviePoster = (EditText) rootView.findViewById(R.id.et_movie_poster);
        etMovieReleaseDate = (EditText) rootView.findViewById(R.id.et_movie_release_date);
        etMovieOverview = (EditText) rootView.findViewById(R.id.et_movie_overview);
        etMovieOriginalLanguage = (EditText) rootView.findViewById(R.id.et_movie_original_language);
        etMovieBackDrop = (EditText) rootView.findViewById(R.id.et_movie_back_drop);
        rbMovieVoteAverage = (RatingBar) rootView.findViewById(R.id.rb_movie_vote_average);
        tvMovieGenres = (TextView) rootView.findViewById(R.id.tv_movie_genres);
        spinGenreMovie = (Spinner) rootView.findViewById(R.id.spinner_genre_movie);
        btAddGenre = (Button) rootView.findViewById(R.id.bt_add_genre);
        btAddGenre.setOnClickListener(onClickListener());

        genresList = MovieGenre.getGenresNamesLst();
        genresList.add(0, "Gênero");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, genresList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGenreMovie.setAdapter(adapter);

    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentGenres = tvMovieGenres.getText().toString();
                int genrePosition = spinGenreMovie.getSelectedItemPosition();
                String newGenre = genresList.get(genrePosition);
                if(currentGenres.equals("")) {
                    currentGenres = "(" +  genresList.get(genrePosition) + ")";
                } else {
                    currentGenres = currentGenres.substring(0, currentGenres.length() - 1) + ", " + genresList.get(genrePosition) + ")";
                }

                tvMovieGenres.setText(currentGenres);
                Log.d(TAG, "Gêneros: " + currentGenres);
            }
        };
    }


}
