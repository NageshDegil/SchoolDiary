package com.pawan.schooldiary.registerOrLogin.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

@EFragment(R.layout.fragment_register)
public class RegisterFragment extends Fragment {

    @ViewById(R.id.button_register)
    Button buttonRegister;

    @ViewById(R.id.button_login)
    Button buttonLogin;

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.radio_group_type)
    RadioGroup radioGroup;

    @NotEmpty
    @ViewById(R.id.name)
    EditText registeredName;

    @Email
    @ViewById(R.id.edit_text_email)
    EditText editTextEmail;

    @Password(min = 6)
    @ViewById(R.id.edit_text_password)
    EditText editTextPassword;

    @ConfirmPassword
    @ViewById(R.id.confirm_password)
    EditText confirmTextPassword;

    private Validator registerInvalidator;

    @AfterViews
    public void init() {

        registerInvalidator = new Validator(this);

        registerInvalidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                register();
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
        LoginFragment_ loginFragment = new LoginFragment_();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginFragment).commit();
    }


    @Click(R.id.button_register)
    void register() {
        Utils.generateToast(getActivity(), "Register button clicked", true);

    }


}

