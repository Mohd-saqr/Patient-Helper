package com.patient.patienthelper.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.helperClass.HashTable.HashTable;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static List<AuthUserAttribute> userAttributes = new ArrayList<>();
    private TextInputEditText email;
    private TextInputEditText password;
    private MaterialButton loginBtn;
    private TextView signupBtn;
    private CheckBox deviceRememberCheckBox;
    private TextView forgetPassword;
    private String emailString;
    private String passwordString;
    Animation scaleDown, scaleUp;
    LottieAnimationView loading;
    UserLogIn userLogIn;
    MySharedPreferences mySharedPreferences;
    HashTable hashTable = new HashTable<>(20);
    private static String tokenFromFirebase;
    private boolean isFirstTime = false;
    private TextInputLayout emailLayout, passwordLayout;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mySharedPreferences = new MySharedPreferences(this);
        inflateViews();
        setUPButton();
        setAllViewsAnim();

    }

    private void setAllViewsAnim() {
        setAnim(forgetPassword);
        setAnim(email);
        setAnim(password);
        setAnim(deviceRememberCheckBox);
        setAnim(loginBtn);
        setAnim(signupBtn);

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
        scaleDown = AnimationUtils.loadAnimation(this, (R.anim.scale_down));
        scaleUp = AnimationUtils.loadAnimation(this, (R.anim.scale_up));
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.signin_btn);
        signupBtn = findViewById(R.id.create_account_button);
        deviceRememberCheckBox = findViewById(R.id.remember_device_checkBox);
        loading = findViewById(R.id.loading);
        forgetPassword = findViewById(R.id.forget_password);
        emailLayout = findViewById(R.id.login_email_input_layout);
        passwordLayout = findViewById(R.id.login_password_input_layout);

    }

    // method to hold Listeners
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUPButton() {

        signupBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


        });

        loginBtn.setOnClickListener(view -> {
            getAllAsString();
            loading.setVisibility(View.VISIBLE);
            loginButtonAction();
        });

        forgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgetPasswordActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });
    }

    //  get all the strings from views
    private void getAllAsString() {

        emailString = email.getText().toString().trim();
        passwordString = password.getText().toString().trim();
    }

    //    validate email
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loginButtonAction() {

        if (TextUtils.isEmpty(emailString) || !emailString.contains("@")) {

            emailLayout.setError("Enter valid Email");
            loading.setVisibility(View.INVISIBLE);

        } else if (TextUtils.isEmpty(passwordString)) {

            passwordLayout.setError("Enter Password");
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                        loading.setVisibility(View.INVISIBLE);
                    } else {

                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.INVISIBLE);
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onlineFetchCurrentUserAttributes() {

        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    userAttributes.clear();
                    Log.i(TAG, "User attributes = " + attributes);
                    userAttributes = attributes;
                    saveUserData();
                    getDeviceToken(userAttributes.get(0).getValue());
                    runOnUiThread(() -> {

                        if (checkFirstLogin()) {
                            loading.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(LoginActivity.this, LookingForActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        } else {
                            getDisease();
                            loading.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        }
                        finish();
                    });
                },
                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
        );
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void getDisease() {
        Gson gson = new Gson();

        hashTable = gson.fromJson(mySharedPreferences.getString("ApiData", null), HashTable.class);
        Disease disease = (Disease) hashTable.get(userLogIn.getDiseaseName());
        mySharedPreferences.putString("userDisease", gson.toJson(disease));
        mySharedPreferences.apply();
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

        System.out.println(userAttributes + "oooooooooooooo");


        String fullName = userAttributes.get(3).getValue() + " " + userAttributes.get(5).getValue();
        String diseaseName = userAttributes.get(4).getValue();
        String id = userAttributes.get(0).getValue();
        Boolean email_verified = userAttributes.get(1).getValue().equals("true");
        String firstName = userAttributes.get(3).getValue();
        String lastName = userAttributes.get(5).getValue();
        String email = userAttributes.get(6).getValue();
        String status = userAttributes.get(2).getValue();
        Boolean firstLogin = status.equals("test");
        String imageId = email.replace("@", "")
                .replace("_", "").replace("-", "")
                .replace(".", "") + "jpg";

        userLogIn = new UserLogIn(fullName, firstName, firstName, lastName, id, email, email_verified, firstLogin, status, imageId, diseaseName);
        userLogIn.setPassword(passwordString);
        System.out.println(userLogIn);
//        User attributes = [AuthUserAttribute {key=AuthUserAttributeKey
//    {attributeKey=sub}, value=2c96687f-cbea-424c-81a0-195bea94e5e8},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=email_verified}, value=true},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=custom:status1}, value=Drug conflict},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=name}, value=cghbdcgdf},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=custom:user_disease}, value=null},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=family_name}, value=fdgfdgdfgd},
//        AuthUserAttribute {key=AuthUserAttributeKey {attributeKey=email}, value=hashemsmadi98@gmail.com}]


        final Gson gson = new Gson();
        String serializedObject = gson.toJson(userLogIn);
        mySharedPreferences.putString("userLog", serializedObject);

        mySharedPreferences.apply();
    }

    private Boolean checkFirstLogin() {
        return userLogIn.getFirstLogIn();
    }

    private void getDeviceToken(String userId) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                Log.i(TAG, "onComplete: Token from google -> " + task.getResult());
                tokenFromFirebase = task.getResult();
                addTokenToCloud(userId, tokenFromFirebase);
            }
        });
    }

    private void addTokenToCloud(String userId, String tokenFromFirebase1) {
        List<Token> tokens = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Token.class, Token.TOKEN_ID.contains(tokenFromFirebase1)),
                response -> {
                    Log.i("hihihashem", "nodata");
                    isFirstTime = true;


                    for (Token token1 : response.getData()) {
                        Log.i(TAG, "Token id -> " + token1.getTokenId());
                        tokens.add(token1);
                        deleteToken(token1, userId, tokenFromFirebase1);
                    }

                    if (tokens.size() == 0) {
                        createNewToken(userId, tokenFromFirebase1);
                    }


                },
                error -> Log.e(TAG, "Query failure", error)
        );
    }

    private void deleteToken(Token token, String userID, String tokenFromFirebase2) {

        Amplify.API.mutate(ModelMutation.delete(token),
                response -> {
                    createNewToken(userID, tokenFromFirebase2);
                },
                error -> {
                    Log.e(TAG, "delete failed", error);
                }
        );
    }

    private void createNewToken(String userID, String tokenId) {
        Token createdToken = Token.builder()
                .tokenId(tokenId)
                .userId(userID)
                .build();
        Amplify.API.mutate(
                ModelMutation.create(createdToken),
                pass -> Log.i(TAG, "Added Token with id: " + pass.getData().getId()),
                error -> Log.e(TAG, "Create failed", error)
        );

    }
}
