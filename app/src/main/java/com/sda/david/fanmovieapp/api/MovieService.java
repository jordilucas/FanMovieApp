package com.sda.david.fanmovieapp.api;

import com.sda.david.fanmovieapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.sda.david.fanmovieapp.util.UrlConstants.*;

/**
 * Created by david on 29/04/2017.
 */

public interface MovieService {

    @GET(URL_MOVIE_FIND_ONE)
    Call<Movie> findOne(@Query("id") Long id);

    @GET(URL_MOVIE_FIND_ALL)
    Call<List<Movie>> findAll();

    @POST(URL_MOVIE_ADD)
    Call<Movie> addMovie(@Body Movie movie);

    @POST(URL_MOVIE_REMOVE)
    Call<String> removeMovie(@Query("id") Long id);

    @GET(URL_MOVIE_TOP_10)
    Call<List<Movie>> findTop10();

}
