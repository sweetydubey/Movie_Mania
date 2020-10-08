package com.example.moviemania;

import android.content.Intent;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getClientCallbackAsync() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
    }
    public static <S> S createService(Class<S> serviceClass) {
        return getClient().create(serviceClass);
    }
    public static <S> S createServiceCallbackAsync(Class<S> serviceClass) {
        return getClientCallbackAsync().create(serviceClass);
    }
    private static OkHttpClient getHttpClient(){
        final AppController appContext = AppController.getInstance();

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }
        return okHttpClientBuilder.build();
    }



}
