package com.patient.patienthelper.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.patient.patienthelper.BuildConfig;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.patient.patienthelper.R;
import com.patient.patienthelper.data.Pharmacy;

import java.util.Arrays;
import java.util.List;

@SuppressLint("ResourceAsColor")
public class PharmacyDetailsActivity extends AppCompatActivity {

    private static final String TAG = PharmacyDetailsActivity.class.getSimpleName();

    private static TextView pharmacyName, pharmacyOpen, pharmacyDistance, pharmacyMobileNumber,pharmacyDirectionMe,pharmacyAddress,pharmacyOpeningHours;

    private static String pharmacyIdString,pharmacyNameString, pharmacyOpenString, pharmacyDistanceString, pharmacyMobileNumberString,pharmacyDirectionMeString,pharmacyAddressString,pharmacyOpeningHoursString;

    private static double pharmacyRatingDouble,pharmacyLat,pharmacyLng,userLat,userLng;

    private static Intent intent;

    private static AppCompatRatingBar pharmacyRating;

    private String apiKey;

    private PlacesClient placesClient;

    private static Pharmacy pharmacy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_details);

        findAllViewById();

        initializeThePlacesAPIClient();

        getPharmacyData();

        setOnClickListeners();
    }

    private void findAllViewById(){
        apiKey = BuildConfig.Places_API_key;
        Log.i(TAG, "findAllViewById: api key -> "+apiKey);
        pharmacyName = findViewById(R.id.pharmacy_name);
        pharmacyOpen = findViewById(R.id.is_pharmacy_open);
        pharmacyDistance = findViewById(R.id.distance_pharmacy);
        pharmacyMobileNumber = findViewById(R.id.pharmacy_mobile_number);
        pharmacyDirectionMe = findViewById(R.id.pharmacy_direction_me);
        pharmacyRating = findViewById(R.id.rating);
        pharmacyAddress = findViewById(R.id.address_pharmacy);
        pharmacyOpeningHours = findViewById(R.id.opening_hour);
        intent = getIntent();
    }

    private void initializeThePlacesAPIClient() {

        // Initialize the SDK
        Places.initialize(this, apiKey);

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this);
    }

    private void getPharmacyData(){

        pharmacyIdString = intent.getStringExtra("Id");
        pharmacyOpenString = intent.getStringExtra("IsOpen");
        userLat = intent.getDoubleExtra("userLatitude",0.0);
        userLng = intent.getDoubleExtra("userLongitude",0.0);
        getPlaceInfoById(pharmacyIdString);
    }

    private void setPharmacyDataOnPage(){

        pharmacyName.setText(pharmacyNameString);
        if (pharmacyOpenString.equals("false")){
            pharmacyOpen.setText("Closed");
            pharmacyOpen.setTextColor(R.color.red);
        }
        pharmacyRating.setRating((float) pharmacyRatingDouble);
        pharmacyMobileNumber.setText(pharmacyMobileNumberString);
        pharmacyAddress.setText(pharmacyAddressString);
        pharmacyOpeningHours.setText(pharmacyOpeningHoursString);
    }

    private void setOnClickListeners(){

        pharmacyDirectionMe.setOnClickListener(view -> {
            openGoogleMap();
        });
        pharmacyMobileNumber.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(pharmacyMobileNumberString));
            startActivity(intent);
        });
    }

    private void openGoogleMap(){

        String uri = "http://maps.google.com/maps?saddr=" + pharmacyLat + "," + pharmacyLng + "&daddr=" + userLat + "," + userLng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void getPlaceInfoById(String id) {
        pharmacy = new Pharmacy();

        // Define a Place ID.
        final String placeId = id;
        Log.i(TAG, "getPlaceInfoById: placeId -> "+placeId);
        // Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.OPENING_HOURS,Place.Field.PHONE_NUMBER,Place.Field.LAT_LNG,Place.Field.RATING);

        // Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            pharmacyNameString = place.getName();
            pharmacyLat = place.getLatLng().latitude;
            pharmacyLng = place.getLatLng().longitude;
            pharmacyAddressString = place.getAddress();
            pharmacyRatingDouble = place.getRating();
            pharmacyMobileNumberString = place.getPhoneNumber();
            pharmacyOpeningHoursString = place.getOpeningHours().getPeriods().get(0).getOpen().getTime().getHours()+":"+place.getOpeningHours().getPeriods().get(0).getOpen().getTime().getMinutes();
            Log.i(TAG, "getPlaceInfoById: place.getName()"+place.getName());
            Log.i(TAG, "getPlaceInfoById: place.getLatLng().latitude "+place.getLatLng().latitude);
            Log.i(TAG, "getPlaceInfoById: place.getLatLng().longitude "+place.getLatLng().longitude);
            Log.i(TAG, "getPlaceInfoById: place.getAddress() "+place.getAddress());
            Log.i(TAG, "getPlaceInfoById: place.getRating()"+place.getRating());
            Log.i(TAG, "getPlaceInfoById: place.getPhoneNumber()"+place.getPhoneNumber());
            Log.i(TAG, "getPlaceInfoById: time "+place.getOpeningHours().getPeriods().get(0).getOpen().getTime().getHours()+":"+place.getOpeningHours().getPeriods().get(0).getOpen().getTime().getMinutes());
            setPharmacyDataOnPage();
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();
                // TODO: Handle error with given status code.
                Log.i(TAG, "getPlaceById: Error -> "+statusCode);
            }
        });

    }

}