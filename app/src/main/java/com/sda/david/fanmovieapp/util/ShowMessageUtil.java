package com.sda.david.fanmovieapp.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by david on 01/05/2017.
 */
public class ShowMessageUtil {

    public static void showMessage(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void longSnackBar(View view, String message) {
//        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        int duration = 5000;
        Snackbar snackbar = Snackbar.make(view, message, duration);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static void longSnackBarWithDuration(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static void longSnackBar(View view, String message, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }

    public static void longSnackBar(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void longSnackBar(View view, int message, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }

    public static void snackBarAction(View view, String message, String actionName, View.OnClickListener action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)
                .show();
    }

    public static void snackBarAction(View view, String message, String actionName, View.OnClickListener action, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }

    public static void snackBarAction(View view, String message, int actionName, View.OnClickListener action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)
                .show();
    }

    public static void snackBarAction(View view, String message, int actionName, View.OnClickListener action, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }

    public static void snackBarAction(View view, int message, int actionName, View.OnClickListener action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)
                .show();
    }

    public static void snackBarAction(View view, int message, int actionName, View.OnClickListener action, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }

    public static void snackBarAction(View view, int message, String actionName, View.OnClickListener action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action)
                .show();
    }

    public static void snackBarAction(View view, int message, String actionName, View.OnClickListener action, int snackBarColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(snackBarColor);
        snackbar.show();
    }
}
