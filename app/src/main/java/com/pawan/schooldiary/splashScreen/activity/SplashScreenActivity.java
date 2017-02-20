package com.pawan.schooldiary.splashScreen.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.parents.activity.ParentsHomeActivity;
import com.pawan.schooldiary.home.teacher.activity.TeacherHomeActivity;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.Utils;
import com.pawan.schooldiary.registerOrLogin.activity.RegisterOrLoginActivity_;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#F39122'>School Diary</font>"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity();
            }
        }, 1000);
    }

    private void startActivity() {
        boolean isLogin = Utils.readPreferenceData(getApplicationContext(), Constants.IS_LOGIN, false);
        Intent intent;
        if(isLogin) {
            if(Utils.readPreferenceData(getApplicationContext(), Constants.LOGIN_TYPE, "").equals("T"))
                intent = new Intent(SplashScreenActivity.this, TeacherHomeActivity.class);
            else
                intent = new Intent(SplashScreenActivity.this, ParentsHomeActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, RegisterOrLoginActivity_.class);
        }
        startActivity(intent);
        finish();
    }
}
