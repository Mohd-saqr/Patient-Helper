package com.patient.patienthelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.patient.patienthelper.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextInputEditText email, verification, newPass;
    private MaterialButton btn;
    private Animation scaleDown, scaleUp;
    private TextInputLayout emailLayout, verificationLayout, newPasswordLayout;
    private ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findAllViewById();
        resetPasswordAction();
        setAllViewsAnim();


        backBtn.setOnClickListener(view -> {
            finish();
        });
    }

    private void setAllViewsAnim() {
        setAnim(btn);
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

    private void findAllViewById() {
        email = findViewById(R.id.forget_password_email);
        verification = findViewById(R.id.forget_act_verification);
        newPass = findViewById(R.id.forget_new_password);
        btn = findViewById(R.id.save_new_password_button);
        scaleDown = AnimationUtils.loadAnimation(this, (R.anim.scale_down));
        scaleUp = AnimationUtils.loadAnimation(this, (R.anim.scale_up));
        emailLayout = findViewById(R.id.forget_password_email_input_layout);
        verificationLayout = findViewById(R.id.forget_password_verification_input_layout);
        newPasswordLayout = findViewById(R.id.forget_password_new_input_layout);
        backBtn=findViewById(R.id.ivBack_for);
    }

    private void resetPasswordAction() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        if (btn.getText().toString().equals("Send Code")) {
            if (TextUtils.isEmpty(email.getText())) {
                emailLayout.setError("Enter Email");
            } else
                sendVerificationCode();
        } else {
            if (TextUtils.isEmpty(newPass.getText())) {
                newPasswordLayout.setError("Please enter password");
            } else if (TextUtils.isEmpty(verification.getText())) {
                verificationLayout.setError("Enter verification code");
            } else {

                Amplify.Auth.confirmResetPassword(
                        newPass.getText().toString(),
                        verification.getText().toString(),
                        () -> {
                            Log.i("TAG", "New password confirmed");
                            startActivity(new Intent(this, LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        },
                        error -> {
                            Log.e("TAG", error.toString());
                            if (error.getMessage().equals("The password given is invalid.")){
                                newPasswordLayout.setError(error.getMessage());
                            }else {
                                verificationLayout.setError(error.getMessage());
                            }
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Change can't complete something went wrong", Toast.LENGTH_SHORT).show();
                            });
                        });
            }
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void sendVerificationCode() {
        Amplify.Auth.resetPassword(
                email.getText().toString(),
                result -> {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            verification.setEnabled(true);
                            newPass.setEnabled(true);
                            btn.setText("Confirm");
                            btn.setIcon(getDrawable(R.drawable.confirm));
                            email.setEnabled(false);
                        }
                    });

                    Log.i("TAGrest", result.toString());
                },
                error -> Log.e("errorsend", error.toString())
        );
    }
}