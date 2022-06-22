package com.patient.patienthelper.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patient.patienthelper.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViewById();

        setUpTheBottomNavigationMovement();

    }

    private void findAllViewById(){
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this,R.id.nav_fragment);

    }
    private void setUpTheBottomNavigationMovement(){
        //enable the swap between the fragments by set up the nav controller
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}

/*
 * How to create a bottom navigation view
 * - create new resources folder inside res and add navigation_graph inside it.
 * - A navigation graph is an XML resource file that contains all of your application destinations and actions.
 * - The navigation file will contain all the destinations(fragments) of our application.
 * - create new resources folder represent the menu resources and add bottom navigation xml file inside it.
 * - bottom navigation contain the items of the bottom navigation view.
 * - define a host fragment in the main layout (activity_main.xml). A nav host fragment is an empty container where fragments are swapped in and out as a user navigates.
 * - Add Menu Item to Bottom Navigation View in activity_main.xml.
 * - enable the swap between the fragments by set up the nav controller
 * - https://www.youtube.com/watch?v=lFeWESYOkEo&ab_channel=CodeWithMazn -> good reference
 */