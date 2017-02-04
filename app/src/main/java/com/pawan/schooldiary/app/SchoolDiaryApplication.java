package com.pawan.schooldiary.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.EApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by pawan on 17/1/17.
 */

@EApplication
public class SchoolDiaryApplication extends Application {
    public Retrofit retrofit;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        context = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void initApp() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Context getContext() {
        return context;
    }
}
