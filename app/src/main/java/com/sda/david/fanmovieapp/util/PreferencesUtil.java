package com.sda.david.fanmovieapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 06/05/2017.
 */
@SuppressWarnings("unused")
public final class PreferencesUtil {

    private static PreferencesUtil instance;

    private final static String PREFS = "fanmovie_prefs";
    private SharedPreferences sharedPreferences;

    public static PreferencesUtil getInstance(Context contexto) {
        if (instance == null) {
            instance = new PreferencesUtil();
            instance.sharedPreferences = contexto.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void saveString(String chave, String valor) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor).apply();
    }

    public void saveInt(String chave, int valor) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(chave, valor).apply();
    }

    public void saveLong(String chave, long valor) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(chave, valor).apply();
    }

    public void saveBoolean(String chave, boolean valor) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(chave, valor).apply();
    }

    public String getString(String chave, String valorPadrao) {
        return sharedPreferences.getString(chave, valorPadrao);
    }

    public int getInt(String chave, int valorPadrao) {
        return sharedPreferences.getInt(chave, valorPadrao);
    }

    public long getLong(String chave, long valorPadrao) {
        return sharedPreferences.getLong(chave, valorPadrao);
    }

    public boolean getBoolean(String chave, boolean valorPadrao) {
        return sharedPreferences.getBoolean(chave, valorPadrao);
    }

}
