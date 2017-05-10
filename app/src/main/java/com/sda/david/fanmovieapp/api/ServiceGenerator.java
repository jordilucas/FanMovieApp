package com.sda.david.fanmovieapp.api;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.login.LoginActivity;
import com.sda.david.fanmovieapp.util.ShowMessageUtil;

import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 29/04/2017.
 */

public class ServiceGenerator {

    private static final String API_BASE_URL_DEVELOPMENT = "http://192.168.25.238:8080";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }).create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_DEVELOPMENT)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static void verifyErrorResponse(int errorCode, View view, Context ctx, boolean loginScreen, FragmentActivity activity) {
        if (errorCode == 501) {
            ShowMessageUtil.longSnackBar(view, ctx.getString(R.string.unavailable_server));
        } else if (errorCode == 503) {
            ShowMessageUtil.longSnackBar(view, ctx.getString(R.string.not_found_server));
        } else if (errorCode == 401) {
            if (loginScreen)
                ShowMessageUtil.longSnackBar(view, ctx.getString(R.string.invalid_login_or_password));
            else {
                Intent intent = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(intent);
                activity.finish();
            }
        } else {
            ShowMessageUtil.longSnackBar(view, ctx.getString(R.string.something_went_wrong));
        }

    }

}
