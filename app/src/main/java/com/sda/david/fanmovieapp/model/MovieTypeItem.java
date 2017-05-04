package com.sda.david.fanmovieapp.model;

import java.util.List;

/**
 * Created by david.melo on 04/05/2017.
 */

public class MovieTypeItem {

    private List<Movie> movies;

    private String genreTitle;

    private Type type;

    public MovieTypeItem(String genreTitle, Type type) {
        this.genreTitle = genreTitle;
        this.type = type;
    }

    public MovieTypeItem(List<Movie> movies, Type type) {
        this.movies = movies;
        this.type = type;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public Type getType() {
        return type;
    }

    public enum Type{
        TITLE, CONTENT;
    }

}
