package com.pawan.schooldiary.splashScreen.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.parent.activity.ParentHomeActivity;
import com.pawan.schooldiary.registerOrLogin.activity.RegisterOrLoginActivity_;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, RegisterOrLoginActivity_.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
