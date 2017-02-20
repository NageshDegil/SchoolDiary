package com.pawan.schooldiary.registerOrLogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.registerOrLogin.fragment.LoginFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_register_or_login)
public class RegisterOrLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_login);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#F39122'>School Diary</font>"));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new LoginFragment_())
                .commit();
    }
}
