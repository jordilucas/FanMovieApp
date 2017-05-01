package com.sda.david.fanmovieapp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sda.david.fanmovieapp.R;

/**
 * Created by david on 01/05/2017.
 */

public class SignupFragment extends Fragment {

    public static final String TAG = "FavoriteFrag";

    private EditText etName;
    private EditText etLogin;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btSignup;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponents(rootView);

        return rootView;
    }

    private void initComponents(View rootView) {

        etName = (EditText) rootView.findViewById(R.id.et_name);
        etLogin = (EditText) rootView.findViewById(R.id.et_login);
        etPassword = (EditText) rootView.findViewById(R.id.et_password);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.et_password_confirm);
        btSignup = (Button) rootView.findViewById(R.id.bt_signup);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {

        if(verifyFields()) {
            Log.d(TAG, "permitido");
        } else {
            Log.d(TAG, "não permitido");
        }

    }

    private boolean verifyFields() {
        boolean validFields = true;

        if(etName.getText().toString().matches("")) {
            etName.setError(getString(R.string.empty_name));
            validFields = false;
        }

        if(etLogin.getText().toString().matches("")) {
            etLogin.setError(getString(R.string.empty_login));
            validFields = false;
        }

        if(etPassword.getText().toString().matches("")) {
            etPassword.setError(getString(R.string.empty_password));
            validFields = false;
        }

        if(etConfirmPassword.getText().toString().matches("")) {
            etConfirmPassword.setError(getString(R.string.empty_confirm_password));
            validFields = false;
        }

        if(!etPassword.getText().toString().matches("") && !etConfirmPassword.getText().toString().matches("")
                && !etPassword.getText().toString().matches(etConfirmPassword.getText().toString())) {
            etPassword.setError(getString(R.string.password_not_compatible));
            etConfirmPassword.setError(getString(R.string.password_not_compatible));
            validFields = false;
        }

        return validFields;

    }

}
