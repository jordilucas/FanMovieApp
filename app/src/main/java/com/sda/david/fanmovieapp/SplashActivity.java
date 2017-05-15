package com.sda.david.fanmovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sda.david.fanmovieapp.login.LoginActivity;
import com.sda.david.fanmovieapp.util.PreferencesUtil;

/**
 * Created by david on 29/04/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final PreferencesUtil preferencesUtil = PreferencesUtil.getInstance(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!preferencesUtil.getString("preferences_login", "").equals(""))
                    callHomeScreen();
                else
                    callLoginScreen();
            }
        }, 2000);

    }

    private void callLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void callHomeScreen() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }
}
