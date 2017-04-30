package com.sda.david.fanmovieapp.util;

import android.util.SparseArray;

import java.util.HashMap;

/**
 * Created by david on 30/04/2017.
 */

public class MovieGenre {

    public static String getGenreNameById(long genreCode) {

        SparseArray<String> map = new SparseArray<>();

        map.put(28, "Ação");
        map.put(12, "Aventura");
        map.put(16, "Animação");
        map.put(35, "Comédia");
        map.put(80, "Crime");
        map.put(99, "Documentário");
        map.put(18, "Drama");
        map.put(10751, "Família");
        map.put(14, "Fantasia");
        map.put(36, "História");
        map.put(27, "Terror");
        map.put(10402, "Música");
        map.put(9648, "Mistério");
        map.put(10749, "Romance");
        map.put(878, "Ficção científica");
        map.put(10770, "Cinema TV");
        map.put(53, "Thriller");
        map.put(10752, "Guerra");
        map.put(37, "Faroeste");

        return map.get((int) genreCode);
    }

}
