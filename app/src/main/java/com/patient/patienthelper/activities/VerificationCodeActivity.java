package com.patient.patienthelper.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.patient.patienthelper.R;

public class VerificationCodeActivity extends AppCompatActivity {
    private static final String TAG = VerificationCodeActivity.class.getSimpleName();
    private TextInputEditText verificationCode;
    private MaterialButton submitBtn;
    private String userEmailString;
    private Context context = this;
    Animation scaleDown, scaleUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification_code);
        inflateViews();
        getUserEmailFromIntent();
        setUPUpButton();
        setAllViewsAnim();
    }

    private void setAllViewsAnim() {
        setAnim(submitBtn);
    }

    private void setAnim(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    view.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    view.startAnimation(scaleDown);
                }

                return false;
            }
        });
    }

    //    inflate all views to be able to reach
    private void inflateViews() {
        verificationCode = findViewById(R.id.verification_code);
        submitBtn = findViewById(R.id.save_new_password_button);
        scaleDown = AnimationUtils.loadAnimation(this, (R.anim.scale_down));
        scaleUp = AnimationUtils.loadAnimation(this, (R.anim.scale_up));


    }

    // get Email From SignUP Activity
    private void getUserEmailFromIntent() {

        userEmailString = getIntent().getStringExtra("emailFromSignUp");
    }

    // Verification using cognito
    private void Verification() {

        String verificationCodeString = verificationCode.getText().toString();
        Amplify.Auth.confirmSignUp(
                userEmailString,
                verificationCodeString,
                result -> {
                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                },
                error -> {
                    {
                        runOnUiThread(() -> {
                            Toast.makeText(context, "Wrong verification code", Toast.LENGTH_SHORT).show();
                        });
                        Log.e(TAG, "Check error => " + error);
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
                startActivity(new Intent(context, LookingForActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}