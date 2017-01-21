package com.pawan.schooldiary.registerOrLogin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.teacher.activity.TeacherHomeActivity;
import com.pawan.schooldiary.registerOrLogin.model.Login;
import com.pawan.schooldiary.registerOrLogin.service.LoginService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @ViewById(R.id.button_login)
    Button buttonLogin;

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.radio_group_type)
    RadioGroup radioGroup;

    @ViewById(R.id.edit_text_email)
    EditText editTextEmail;

    @ViewById(R.id.edit_text_password)
    EditText editTextPassword;

    private LoginService loginService;

    @AfterViews
    public void init() {
        loginService = schoolDiaryApplication.retrofit.create(LoginService.class);
    }


    @Click(R.id.button_login)
    void login() {

        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedID);


        loginService.login(new Login(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), radioButton.getText().toString().trim().equals("Teacher") ? "T" : "P"))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                    // TODO show alert for error
                               }

                               @Override
                               public void onNext(User user) {
                                   // TODO save type T/P in SP and user email according to type
                                   Intent intent = new Intent(getActivity(), TeacherHomeActivity.class);
                                   startActivity(intent);
                               }
                           }
                );



    }

}
