package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.patient.patienthelper.R;

public class Splash extends AppCompatActivity {
    // to handle splash
    Handler splashHandler= new Handler();

    private static final String TAG = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeAws();
        splashDelay();
        startActivity(new Intent(this, LoginActivity.class));


    }
    // this method for display the splash for a few seconds
    public void splashDelay(){
        splashHandler.postDelayed(() -> finish(),10000);
    }




    /**
     * this method for initialize aws stuff
     */
    private void initializeAws() {
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
    }



}