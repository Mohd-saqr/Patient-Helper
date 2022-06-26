package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieConfig;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

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
    LottieAnimationView loading;

    MySharedPreferences mySharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mySharedPreferences = new MySharedPreferences(this);
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
        loading=findViewById(R.id.loading);
    }

    // method to hold Listeners
    private void setUPButton() {

        signupBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        loginBtn.setOnClickListener(view -> {
            getAllAsString();
            loading.setVisibility(View.VISIBLE);
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
            loading.setVisibility(View.INVISIBLE);

        } else if (TextUtils.isEmpty(passwordString)) {

            password.setError("Enter Password");
            loading.setVisibility(View.INVISIBLE);

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
                    if (result.isSignInComplete()) {
                        if (deviceRememberCheckBox.isChecked()) {

                            rememberDevice();
                        }
                        onlineFetchCurrentUserAttributes();

                        savePasswordSharedPreferences();

                        runOnUiThread(()->{
                            if (checkFirstLogin()){
                                loading.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(LoginActivity.this, LookingForActivity.class));
                            }else{
                                loading.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        });
                    } else {

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
                    saveUserData();
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

//    //  intent to home
//    private void navigateToActivity(Class activity) {
//        progressBar.setVisibility(View.INVISIBLE);
//        startActivity(new Intent(LoginActivity.this, activity.class));
//        finish();
//
//    }

    private void saveUserData() {
        String fullName = userAttributes.get(2).getValue() + " " + userAttributes.get(3).getValue();
        String id = userAttributes.get(0).getValue();
        String email = userAttributes.get(4).getValue();
        String imageId = email + ".jpg";
        Boolean email_verified = userAttributes.get(1).getValue().equals("true");
        UserLogIn userLogIn = new UserLogIn(fullName, fullName, id, email, email_verified, imageId, userAttributes.get(2).getValue(), userAttributes.get(3).getValue(), passwordString);
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(userLogIn);
        mySharedPreferences.putString("userLog", serializedObject);
        mySharedPreferences.apply();
    }

    private Boolean checkFirstLogin() {
        return mySharedPreferences.getBoolean("FirstLog", true);
    }


}