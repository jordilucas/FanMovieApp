package com.sda.david.fanmovieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;

/**
 * Created by david on 29/04/2017.
 */

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter adapter;

    private EditText etLogin;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    private String[] phrasesIntro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phrasesIntro = new String[]{"Seja bem vindo !! \n Procure os seus filmes favoritos aqui", "Salve todos para ter acesso fácil as suas informações no futuro", "Veja sempre quais sãos os top 10 filmes atuais"};

        initComponents();
    }

    private void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.vp_login);

        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btRegister = (Button) findViewById(R.id.bt_register);

        btLogin.setOnClickListener(onClickListener());
        btRegister.setOnClickListener(onClickListener());

        adapter = new LoginViewPageAdapter(this, phrasesIntro);
        viewPager.setAdapter(adapter);
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
        finish();
    }
}
