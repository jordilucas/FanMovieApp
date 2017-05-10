package com.sda.david.fanmovieapp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.api.ServiceGenerator;
import com.sda.david.fanmovieapp.api.interfaces.UserService;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.util.PreferencesUtil;
import com.sda.david.fanmovieapp.util.ShowMessageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by david on 01/05/2017.
 */

public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFrag";

    private PreferencesUtil preferencesUtil;

    private EditText etLogin;
    private EditText etPassword;
    private Button btLogin;

    private ProgressDialog dialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(rootView);

        return rootView;
    }

    private void initComponents(View rootView) {
        etLogin = (EditText) rootView.findViewById(R.id.et_login);
        etPassword = (EditText) rootView.findViewById(R.id.et_password);
        btLogin = (Button) rootView.findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void login() {
        if(verifyFields()) {
            requestLogin();
        }
    }

    private void requestLogin() {
        dialog.setMessage(getString(R.string.loading_login));
        dialog.show();
        User user = new User(etLogin.getText().toString(), etPassword.getText().toString());
        Call<User> call = ServiceGenerator.createService(UserService.class, user.getUsername(), user.getPassword()).loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if(response.isSuccessful()) {
                    preferencesUtil = PreferencesUtil.getInstance(getContext());
                    preferencesUtil.saveString("preferences_login", etLogin.getText().toString());
                    callHomeScreen(response.body());
                } else {
                    ServiceGenerator.verifyErrorResponse(response.code(), etLogin, getContext(), true, getActivity());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                ShowMessageUtil.longSnackBar(etLogin, getString(R.string.something_went_wrong));
            }
        });
    }

    private void callHomeScreen(User user) {

        Intent intent = new Intent(getContext(), BaseActivity.class);
        intent.putExtra(BaseActivity.ARG_USER, user);
        startActivity(intent);
//        overridePendingTransition(R.anim.res_anim_fadein, R.anim.res_anim_fadeout);
        getActivity().finish();
    }

    private boolean verifyFields() {
        boolean validFields = true;

        if(etLogin.getText().toString().matches("")) {
            etLogin.setError(getString(R.string.empty_login));
            validFields = false;
        }

        if(etPassword.getText().toString().matches("")) {
            etPassword.setError(getString(R.string.empty_password));
            validFields = false;
        }

        return validFields;

    }

}
