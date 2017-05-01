package com.sda.david.fanmovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by david on 29/04/2017.
 */

public class User {

    private Long id;

    private String name;

    @SerializedName("user_name")
    private String userName;

    private String password;

    @SerializedName("favorites_movies")
    private Set<Long> favoritesMovies;

    private boolean administrator;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String name, String userName, String password, Set<Long> favoritesMovies, boolean administrator) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.favoritesMovies = favoritesMovies;
        this.administrator = administrator;
    }

    public User(Long id, String name, String userName, Set<Long> favoritesMovies, boolean administrator) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.favoritesMovies = favoritesMovies;
        this.administrator = administrator;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Set<Long> getFavoritesMovies() {
        return favoritesMovies;
    }

    public boolean isAdministrator() {
        return administrator;
    }
}
