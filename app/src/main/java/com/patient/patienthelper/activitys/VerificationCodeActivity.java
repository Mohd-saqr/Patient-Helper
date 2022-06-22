package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

public class VerificationCodeActivity extends AppCompatActivity {
    private static final String TAG = VerificationCodeActivity.class.getSimpleName();
    private TextInputEditText verificationCode;
    private RelativeLayout submitBtn;
    private String userEmailString;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_code);
        inflateViews();
        getUserEmailFromIntent();
        setUPUpButton();

    }
    //    inflate all views to be able to reach
    private void inflateViews(){
        verificationCode = findViewById(R.id.verification_code);
        submitBtn = findViewById(R.id.verification_code_button);


    }
    // get Email From SignUP Activity
    private void getUserEmailFromIntent(){

        userEmailString = getIntent().getStringExtra("emailFromSignUp");
    }
    // Verification using cognito
    private void Verification(){

        String verificationCodeString = verificationCode.getText().toString();
        Amplify.Auth.confirmSignUp(
                userEmailString,
                verificationCodeString,
                result -> {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                },
                error -> {
                    {
                        runOnUiThread(() -> {
                            Toast.makeText(context, "Wrong verification code", Toast.LENGTH_SHORT).show();
                        });
                        Log.e(TAG, "Check error => "+error);
                    }
                }
        );
    }
    // method to hold Listeners
    private void setUPUpButton() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verification();
                startActivity(new Intent(context,LookingForActivity.class));
                finish();

            }
        });
    }

}