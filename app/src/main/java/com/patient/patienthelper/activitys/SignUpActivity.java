package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    Context context = this ;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private TextInputEditText firstNameSignup ;
    private TextInputEditText lastNameSignup ;

    private TextInputEditText emailSignup;
    private TextInputEditText passwordSignup ;
    private TextView signUpButton;
    private static String firstNameSignupString;
    private static String lastNameSignupString;
    private static String emailSignupString;
    private static String passwordSignupString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameSignup = findViewById(R.id.first_name);
        lastNameSignup = findViewById(R.id.last_name);
        emailSignup = (TextInputEditText)findViewById(R.id.signup_email);
        passwordSignup = findViewById(R.id.signup_password);
        signUpButton = findViewById(R.id.signup_button);


        setUPpSignUpButton();

    }
    private void setUPpSignUpButton(){

        signUpButton.setOnClickListener(view -> {
            firstNameSignupString = firstNameSignup.getText().toString().trim();
            lastNameSignupString = lastNameSignup.getText().toString().trim();
            emailSignupString = emailSignup.getText().toString().trim();
            passwordSignupString = passwordSignup.getText().toString().trim();

            ArrayList<AuthUserAttribute> attributes = new ArrayList<>();
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), emailSignupString));
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.name(), firstNameSignupString));
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.familyName(), lastNameSignupString));

            Log.i(TAG, "setUPpSignUpButton: "+ emailSignupString+".........."+passwordSignupString);

            Amplify.Auth.signUp(
                    emailSignupString,
                    passwordSignupString,
                    AuthSignUpOptions.builder().userAttributes(attributes).build(),
                    result -> {
                        Log.i(TAG, "Result: " + result);
                        runOnUiThread(() -> {
                            Intent intent = new Intent(this, VerificationCodeActivity.class);
                            startActivity(intent);
                            finish();

                        });

                    },
                    error -> {
                        Log.e(TAG, "Sign up failed", error);
                        runOnUiThread(() -> {
                            Toast.makeText(context, "Signup can't complete something went wrong", Toast.LENGTH_SHORT).show();
                            onResume();
                        });

                    }
            );

        });
    }
}