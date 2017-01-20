package com.pawan.schooldiary.registerOrLogin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.teacher.activity.TeacherHomeActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @ViewById(R.id.button_login)
    Button buttonLogin;


    @Click(R.id.button_login)
    void login() {
        Intent intent = new Intent(getActivity(), TeacherHomeActivity.class);
        startActivity(intent);
        // TODO save type T/P in SP and user email according to type
    }

}
