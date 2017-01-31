package com.pawan.schooldiary.registerOrLogin.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.home.parents.activity.ParentsHomeActivity;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.teacher.activity.TeacherHomeActivity;
import com.pawan.schooldiary.home.utils.Constants;
import com.pawan.schooldiary.home.utils.Utils;
import com.pawan.schooldiary.registerOrLogin.model.Login;
import com.pawan.schooldiary.registerOrLogin.service.LoginService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @ViewById(R.id.button_login)
    Button buttonLogin;

    @ViewById(R.id.button_signup)
    Button signupButton;

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.radio_group_type)
    RadioGroup radioGroup;

    @Email
    @ViewById(R.id.edit_text_email)
    EditText editTextEmail;

    @NotEmpty
    @ViewById(R.id.edit_text_password)
    EditText editTextPassword;

    private LoginService loginService;
    private CommonService commonService;
    private Validator logInvalidator;

    @AfterViews
    public void init() {
        logInvalidator = new Validator(this);
        loginService = schoolDiaryApplication.retrofit.create(LoginService.class);
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);

        logInvalidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                login();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getContext());
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Click(R.id.button_login)
    void login() {
        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedID);
        final String type = radioButton.getText().toString().trim().equals("Teacher") ? "T" : "P";
        final String email = editTextEmail.getText().toString().trim();

        loginService.login(new Login(email, editTextPassword.getText().toString().trim(), type))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   try {
                                       if (e != null && e instanceof HttpException) {
                                           if (((HttpException) e).code() == 401) {
                                               Status status = new Gson().fromJson(((HttpException) e).response().errorBody().string().toString(), Status.class);
                                               if (status.getStatus().equals("invalid username or password")) {
                                                   showAlert("Incorrect Password", "The password you entered is incorrect. Please try again.");
                                               } else if (status.getStatus().equals("user does not exist")) {
                                                   showAlert("User is not Registered", "User does not registered with us. Please create account first.");
                                               }
                                           }
                                       }
                                       Utils.networkError(getActivity(), "Network Error", "Please check your internet connectivity.", e);
                                   } catch (Exception e1) {
                                       Utils.networkError(getActivity(), "Network Error", "Please check your internet connectivity.", e);
                                   }
                               }

                               @Override
                               public void onNext(User user) {
                                   Intent intent;
                                   if(type.equals("T"))
                                       intent = new Intent(getActivity(), TeacherHomeActivity.class);
                                   else
                                       intent = new Intent(getActivity(), ParentsHomeActivity.class);
                                   startActivity(intent);
                                   initSP(type, email);
                                   // TODO add token to server
                               }
                           }
                );
    }

    @Click(R.id.button_signup)
    void signUp() {
        RegisterFragment_ registerFragment = new RegisterFragment_();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, registerFragment).commit();

    }

    private void initSP(String type, String email) {
        if(type.equals("T"))
            Utils.savePreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.TEACHER_EMAIL_KEY, email);
        else
            Utils.savePreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.PARENTS_EMAIL_KEY, email);

        Utils.savePreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.LOGIN_TYPE, type);
        Utils.savePreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.IS_LOGIN, true);
        Utils.savePreferenceData(schoolDiaryApplication.getApplicationContext(), Constants.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());
        User user = new User(email, FirebaseInstanceId.getInstance().getToken(), type);
        updateToken(user);
    }

    private void showAlert(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editTextEmail.getText().clear();
                        editTextPassword.getText().clear();
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateToken(User user) {
        commonService.updateToken(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                               }

                               @Override
                               public void onNext(ResponseBody responseBody) {
                               }
                           }
                );
    }

}
