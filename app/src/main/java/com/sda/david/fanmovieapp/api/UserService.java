package com.sda.david.fanmovieapp.api;

import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.User;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.sda.david.fanmovieapp.util.UrlConstants.*;

/**
 * Created by david on 29/04/2017.
 */

public interface UserService {

    @GET(URL_USER_ONE)
    Call<User> findOne(@Query("id") Long id);

    @GET(URL_USER_FIND_ALL)
    Call<List<User>> findAll();

    @POST(URL_USER_REMOVE)
    Call<String> removeMovie(@Query("id") Long id);

    @POST(URL_USER_ADD_FAVORITE)
    Call<User> addFavorite(@Query("id") Long id, @Body Set<Long> idFavorites);

    @POST(URL_USER_REMOVE_FAVORITE)
    Call<User> removeFavorite(@Query("id") Long id, @Body Set<Long> idFavorites);

    @GET(URL_USER_FAVORITES)
    Call<List<Movie>> findFavorites(@Query("id") Long id);

    @POST(URL_USER_LOGIN)
    Call<User> loginUser(@Body User user);

    @POST(URL_USER_SIGNUP)
    Call<User> signupUser(@Body User user);

    @GET(URL_USER_LOGOUT)
    Call<Void> logoutUser();

}
