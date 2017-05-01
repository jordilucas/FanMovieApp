package com.sda.david.fanmovieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by david on 29/04/2017.
 */

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter adapter;
    private CirclePageIndicator mContentIntroIndicator;

    private EditText etLogin;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    private String[] phrasesIntro;
    Timer timer;
    int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phrasesIntro = new String[]{"Seja bem vindo !! \n Procure os seus filmes favoritos aqui", "Salve todos para ter acesso fácil as suas informações no futuro", "Veja sempre quais sãos os top 10 filmes atuais"};

        initComponents();
    }

    private void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.vp_login);
        mContentIntroIndicator = (CirclePageIndicator) findViewById(R.id.cpi_login_intro_indicator);

        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btRegister = (Button) findViewById(R.id.bt_register);

        btLogin.setOnClickListener(onClickListener());
        btRegister.setOnClickListener(onClickListener());

        adapter = new LoginViewPageAdapter(this, phrasesIntro);
        viewPager.setAdapter(adapter);
        mContentIntroIndicator.notifyDataSetChanged();
        mContentIntroIndicator.setViewPager(viewPager);
        pageSwitcher(4);
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_login:
                        login();
                        break;

                    case R.id.bt_register:
                        break;

                }
            }
        };
    }

    private void login() {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.res_anim_fadein, R.anim.res_anim_fadeout);
        finish();
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 2) {
                        page = 0;
                        viewPager.setCurrentItem(page);
                        timer.cancel();
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new RemindTask(), 0, 4 * 1000);
                    } else {
                        viewPager.setCurrentItem(page++);
                    }
                }
            });

        }
    }
}
