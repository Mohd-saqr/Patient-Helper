package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static List<AuthUserAttribute> userAttributes = new ArrayList<>();
    private TextInputEditText email;
    private TextInputEditText password;
    private RelativeLayout loginBtn;
    private TextView signupBtn;
    private CheckBox deviceRememberCheckBox;
    private TextView forgetPassword;
    private String emailString;
    private String passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inflateViews();
        setUPButton();
    }

    //    inflate all views to be able to reach
    private void inflateViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.create_account_button);
        deviceRememberCheckBox = findViewById(R.id.remember_device_checkBox);
    }
    // method to hold Listeners
    private void setUPButton() {

        signupBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        loginBtn.setOnClickListener(view -> {
            getAllAsString();
            loginButtonAction();
        });

//        forgetPassword.setOnClickListener(view -> {
//            startActivity(new Intent(this, ForgetPasswordActivity.class));
//        });
    }
    //  get all the strings from views
    private void getAllAsString() {

        emailString = email.getText().toString().trim();
        passwordString = password.getText().toString().trim();
    }
    //    validate email
    private void loginButtonAction() {

        if (TextUtils.isEmpty(emailString) || !emailString.contains("@")) {

            email.setError("Enter valid Email");

        } else if (TextUtils.isEmpty(passwordString)) {

            password.setError("Enter Password");

        } else {

            login();

        }

        View view2 = this.getCurrentFocus();
        if (view2 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
    }

//    setup aws login

    private void login() {


        Amplify.Auth.signIn(
                emailString,
                passwordString,
                result -> {
                    Log.i(TAG, result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    if (result.isSignInComplete()){
                        if (deviceRememberCheckBox.isChecked()) {

                            rememberDevice();
                        }
                        onlineFetchCurrentUserAttributes();

                        savePasswordSharedPreferences();

                        runOnUiThread(this::navigateToMainActivity);
                    }else {

                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            onResume();
                        });
                    }
                },
                error -> {
                    Log.e(TAG, error.toString());
                    runOnUiThread(() -> {
                        Toast.makeText(this, "username or Password Incorrect", Toast.LENGTH_SHORT).show();
                        onResume();
                    });
                }
        );

    }

    private void rememberDevice() {
        Amplify.Auth.rememberDevice(
                () -> Log.i(TAG, "Remember device succeeded"),
                error -> Log.e(TAG, "Remember device failed with error " + error)
        );
    }
    //    Fetch Current User Attributes to use them later
    public void onlineFetchCurrentUserAttributes() {

        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    userAttributes.clear();
                    Log.i(TAG, "User attributes = " + attributes);
                    userAttributes = attributes;
                },
                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
        );
    }

    //    Save password in local device to remember
    private void savePasswordSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        preferenceEditor.putString(emailString, passwordString);
        preferenceEditor.apply();
    }
    //  intent to home
    private void navigateToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 1500);
    }


}