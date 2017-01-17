package com.pawan.schooldiary.registerOrLogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pawan.schooldiary.R;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_register_or_login)
public class RegisterOrLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_login);
    }
}
