package com.patient.patienthelper.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patient.patienthelper.R;


/**
 * Source
 * https://www.youtube.com/watch?v=pjFcJ6EB8Dg
 */
@SuppressLint({"MissingPermission", "StaticFieldLeak"})
public class NearbyPharmaciesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private String TAG = NearbyPharmaciesActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_pharmacies);

        findAllViewById();

        setUpTheBottomNavigationMovement();
    }

    private void findAllViewById(){
        bottomNavigationView = findViewById(R.id.bottom_navigation_view_nearby_pharmacies);
        navController = Navigation.findNavController(this, R.id.nav_fragment_nearby_pharmacies);
    }

    private void setUpTheBottomNavigationMovement(){
        //enable the swap between the fragments by set up the nav controller
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}