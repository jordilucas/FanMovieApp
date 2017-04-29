package com.sda.david.fanmovieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

    private EditText etLogin;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btRegister = (Button) findViewById(R.id.bt_register);

        btLogin.setOnClickListener(onClickListener());
        btRegister.setOnClickListener(onClickListener());
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
