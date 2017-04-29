package com.sda.david.fanmovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by david on 29/04/2017.
 */

public class Movie {

    private Long _id;

    @SerializedName("poster_path")
    private String posterPath;

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    private Set<Long> genreIds;

    private long id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    private float popularity;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("vote_average")
    private float voteAverage;

    public Movie() {
    }

    public Movie(String posterPath, String overview, String releaseDate, Set<Long> genreIds, long id, String originalTitle, String originalLanguage, String title, String backdropPath, float popularity, long voteCount, float voteAverage) {

        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    public Long get_id() {
        return _id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Set<Long> getGenreIds() {
        return genreIds;
    }

    public long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }
}
