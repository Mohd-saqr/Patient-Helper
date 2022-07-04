package com.patient.patienthelper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.io.File;

import id.ionbit.ionalert.IonAlert;

public class DeleteAccountActivity extends AppCompatActivity {

    private static final String TAG = DeleteAccountActivity.class.getSimpleName();
    private TextInputEditText password;
    private ImageView backBtn;
    private TextView deleteAccountBtn;
    private String currentPassword;
    private UserLogIn userLogIn;
    private IonAlert ionAlert;
    private File fileToDelete;
    private static String imageToUploadKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        getUserInfo();
        findAllViewById();
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getUserInfo() {
        MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        if (mySharedPreferences.contains("userLog")) {
            Gson gson = new Gson();
            userLogIn = gson.fromJson(mySharedPreferences.getString("userLog", "noData"), UserLogIn.class);
            Log.i(TAG, "findAllViewById: Password is -> "+userLogIn.getPassword());
        }
    }

    private void findAllViewById() {

        password = findViewById(R.id.password_delete_account_page);
        deleteAccountBtn = findViewById(R.id.delete_account_btn);
        backBtn = findViewById(R.id.ivBack);
        currentPassword = userLogIn.getPassword();
        imageToUploadKey = userLogIn.getImageId();
        Log.i(TAG, "findAllViewById: Password is -> "+currentPassword);
    }

    private void setOnClickListener() {

        deleteAccountBtn.setOnClickListener(view -> {
            alertDialog();
        });
        backBtn.setOnClickListener(view -> {
            backToProfilePage();
        });
    }

    private void alertDialog() {

        ionAlert = new IonAlert(this, IonAlert.ERROR_TYPE);

        ionAlert.setTitleText("Are you sure!")
                .setConfirmText("Delete")
                .setCancelText("Cancel")
                .setConfirmClickListener(new IonAlert.ClickListener() {
                    @Override
                    public void onClick(IonAlert ionAlert) {
                        passwordCheck();
                    }
                })
                .show();
    }

    private void passwordCheck() {

        if (TextUtils.isEmpty(password.getText())) {
            ionAlert.dismiss();
            password.setError("Enter password");
        } else if (!password.getText().toString().equals(currentPassword)) {
            ionAlert.dismiss();
            password.setError("The password incorrect");
        } else if (password.getText().toString().equals(currentPassword)) {
            deleteAccount();
        }
    }

    private void deleteAccount() {

        Amplify.Auth.deleteUser(
                () -> {
                    Log.i(TAG, "Delete user succeeded");
                    deleteImageFromS3();
                    navigateToLoginPage();
                },
                error -> Log.e(TAG, "Delete user failed with error " + error.toString())
        );

    }

    private void deleteImageFromS3() {
        if (!imageToUploadKey.isEmpty()) {
            Amplify.Storage.remove(
                    imageToUploadKey,
                    success ->{
                        fileToDelete = new File(this.getFilesDir() + "/"+"userProfile"+".jpg");
                        if (fileToDelete.delete()){
                            Log.i(TAG, "deleteImageFromS3: The local file deleted -> true");
                        }
                        Log.i(TAG, "Image successfully deleted "+success.getKey());
                    },
                    failure ->
                    {
                        Log.e(TAG, "Failure in deleting file on S3 with key: " + imageToUploadKey + " with error: " + failure.getMessage());
                    }
            );
        }
    }

    private void navigateToLoginPage(){

        startActivity(new Intent(this,LoginActivity.class));
    }

    private void backToProfilePage() {

        onBackPressed();
        finish();
    }
}