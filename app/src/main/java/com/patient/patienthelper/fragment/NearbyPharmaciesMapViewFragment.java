package com.patient.patienthelper.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.patient.patienthelper.BuildConfig;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.LoginActivity;
import com.patient.patienthelper.activities.NearbyPharmaciesActivity;
import com.patient.patienthelper.helperClass.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressLint({"MissingPermission", "StaticFieldLeak"})
public class NearbyPharmaciesMapViewFragment extends Fragment {

    private final String TAG = NearbyPharmaciesActivity.class.getSimpleName();
    private SupportMapFragment supportMapFragment;
    private FloatingActionButton myLocationBtn;
    private GoogleMap map;
    private AppCompatSpinner spType;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double currentLat = 0, currentLong = 0;
    private String[] placeTypeList;
    private String apiKey;
    private MarkerOptions markerOptionsCurrentLocation;
    private LottieAnimationView loading;
    private Button listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_pharmacies_map_view, container, false);

        findAllViewById(view);

        initializeSpinner();

        initializeFusedLocationProviderClient();

        getCurrentLocation();

        setOnClickListener();

        //setOnClickListener();
        // Inflate the layout for this fragment
        return view;
    }

    private void findAllViewById(View view) {

        apiKey = BuildConfig.Places_API_key;
        spType = view.findViewById(R.id.sp_type);
        listView = view.findViewById(R.id.list_view_button);
        loading = view.findViewById(R.id.loading_in_pharmacies_map);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        myLocationBtn = view.findViewById(R.id.my_location_floating_button);
        loading.setVisibility(View.VISIBLE);
    }

    private void initializeSpinner() {
        //initialize array of place type
        placeTypeList = new String[]{"pharmacy", "hospital", "doctor", "صيدلية", "طبيب", "دكتور", "الدكتور"};
        //initialize array of place name
        String[] placeNameList = {"Pharmacy", "Hospital", "Doctor", "صيدلية", "طبيب", "دكتور", "الدكتور"};
        //set adapter on spinner
        spType.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, placeNameList));
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPlacesUrl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeFusedLocationProviderClient() {
        //initialize fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    private void getCurrentLocation() {

        //initialize task location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success
                if (location != null) {
                    //when location not equal to null
                    //get current latitude
                    currentLat = location.getLatitude();
                    //get current longitude
                    currentLong = location.getLongitude();
                    //sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //when map is ready
                            map = googleMap;
                            markerOptionsCurrentLocation = new MarkerOptions().position(new LatLng(currentLat, currentLong)).title("Your location");

                            map.addMarker(markerOptionsCurrentLocation);
                            //zoom current location on map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 16.0f));
                            setPlacesUrl();
                        }
                    });

                }
            }
        });
    }

    private void setPlacesUrl() {

        //get selected position of spinner
        int i = spType.getSelectedItemPosition();
        Log.i(TAG, "setPlacesUrl: getSelectedItemPosition -> "+i);
        //initialize url
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +//url
                "?location=" + currentLat + "," + currentLong +//location latitude and longitude
                "&radius=5000" +//nearby radius
                "&types=" + placeTypeList[i] +//place type
                "&sensor=true" +//sensor
                "&key=" + apiKey;

        //execute place task method to download json data
        new PlaceTask().execute(url);
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                //initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //execute parser task
            new ParserTask().execute(s);

        }
    }

    private String downloadUrl(String string) throws IOException {
        //initialize url
        URL url = new URL(string);
        //initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connect connection
        connection.connect();
        //initialize input stream
        InputStream stream = connection.getInputStream();
        //initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //initialize string builder
        StringBuilder builder = new StringBuilder();
        //initialize string Variable
        String line = "";
        //use while loop
        while ((line = reader.readLine()) != null) {
            //append line
            builder.append(line);
        }
        //get append data
        String data = builder.toString();
        //close reader
        reader.close();
        //return dat
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //create json parser
            JsonParser jsonParser = new JsonParser();
            //initialize hash map list
            List<HashMap<String, String>> mapList = new ArrayList<>();

            JSONObject object = null;
            try {
                //initialize json object
                object = new JSONObject(strings[0]);
                //parse json object
                Log.i(TAG, "doInBackground: object -> " + object);
                mapList = jsonParser.parseResult(object);
                Log.i(TAG, "doInBackground: mapList -> " + mapList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //return map list
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //clear map
            map.clear();
            //user for loop
            for (int i = 0; i < hashMaps.size(); i++) {
                //initialize hash map
                HashMap<String, String> hashMapList = hashMaps.get(i);
                //get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //get latitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //get name
                String name = hashMapList.get("name");
                //concat lat and long
                LatLng latLng = new LatLng(lat, lng);
                //initialize marker option
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng));

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                //set Position
                markerOptions.position(latLng);
                //set title
                markerOptions.title(name);
                //add marker on map
                map.addMarker(markerOptions);
            }
            map.addMarker(markerOptionsCurrentLocation);
        }
    }

    private void setOnClickListener(){

        listView.setOnClickListener(view -> {
            navigateToMapViewFragment();
        });

        myLocationBtn.setOnClickListener(view -> {
            getCurrentLocation();
        });
    }

    private void navigateToMapViewFragment(){
        Fragment fragment;
        fragment = new NearbyPharmaciesListViewFragment();

        FragmentManager fragmentManager = getFragmentManager(); // For AppCompat use getSupportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.nav_fragment, fragment)
                .commit();
        getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}