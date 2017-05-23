package com.sda.david.fanmovieapp.model

import com.google.gson.annotations.SerializedName
import org.intellij.lang.annotations.Language

/**
 * Created by jordi on 23/05/2017.
 */
data class MovieK(val id : Long, @SerializedName("poster_path") val posterPath : String,
                 @SerializedName("release_date") val releaseDate : String,
                 @SerializedName("genre_ids") val genreIds : List<Long>,
                 @SerializedName("original_title") val originalTitle : String,
                 @SerializedName("original_language") val originalLanguage : String,
                 val title : String, @SerializedName("backdrop_path") val backdropPath : String,
                 val popularity : Float, @SerializedName("vote_count") val voteCount : Long,
                 @SerializedName("vote_averege") val voteAverege : Float)

data class MovieTypeItemK( val movies : List<MovieK>, val genreTitle : String, val type : TypeK)

data class UserK( val id : Long, val name : String, val username : String, val password : String,
                  @SerializedName("favorite_movies") val favoriteMovies : List<Long>)

enum class TypeK {
    TITLE, CONTENT
}