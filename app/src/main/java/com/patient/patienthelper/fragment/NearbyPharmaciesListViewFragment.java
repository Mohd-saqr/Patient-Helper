package com.patient.patienthelper.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.patient.patienthelper.BuildConfig;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.MainActivity;
import com.patient.patienthelper.activities.NearbyPharmaciesActivity;
import com.patient.patienthelper.activities.PharmacyDetailsActivity;
import com.patient.patienthelper.adapters.RecyclerAdapterPharmacy;
import com.patient.patienthelper.data.Pharmacy;
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


@SuppressLint({"MissingPermission", "StaticFieldLeak", "NotifyDataSetChanged"})
public class NearbyPharmaciesListViewFragment extends Fragment {

    private RecyclerView recyclerview;
    private RecyclerAdapterPharmacy recyclerAdapterPharmacy;
    private static final String TAG = NearbyPharmaciesActivity.class.getSimpleName() + " -> " + "NearbyPharmaciesListViewFragment";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double currentLat = 0, currentLong = 0;
    private String apiKey;
    private static List<HashMap<String, String>> listResultToSave;
    private static List<Pharmacy> pharmacies = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private LottieAnimationView loading;
    private ImageView backBtn;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        loading.setVisibility(View.VISIBLE);
        initializeFusedLocationProviderClient();
        askLocationPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_pharmacies_list_view, container, false);

        findAllViewById(view);

        loading.setVisibility(View.VISIBLE);


        initializeFusedLocationProviderClient();

        askLocationPermission();

        onRefreshPullListener();

        setOnClickListener();

        // Inflate the layout for this fragment
        return view;
    }

    private void findAllViewById(View view) {
        apiKey = BuildConfig.Places_API_key;
        recyclerview = view.findViewById(R.id.nearby_pharmacies_recycler_view);
        loading = view.findViewById(R.id.loading_in_pharmacies_list);
        swipeContainer = view.findViewById(R.id.swipe_refresh_layout);
        backBtn = view.findViewById(R.id.ivBack);
    }



    private void initializeFusedLocationProviderClient() {
        //initialize fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    private void askLocationPermission() {
        //check permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                //when permission granted
                //call method
                getCurrentLocation();
            }else {
                Toast.makeText(getContext(), "Please turn on your location....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            //when permission denied
            //request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    private boolean isLocationEnabled() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
                    Log.i(TAG, "onSuccess: LatLng -> " + currentLat + " " + currentLong);
                    setPlacesUrl();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //when permission granted
                //call method
                getCurrentLocation();
            }
        }
    }

    private void setPlacesUrl() {


        //initialize url
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +//url
                "?location=" + currentLat + "," + currentLong +//location latitude and longitude
                "&radius=5000" +//nearby radius
                "&types=" + "pharmacy" +//place type
                "&sensor=true" +//sensor
                "&key=" + apiKey;

        //execute place task method to download json data
        new PlaceTask().execute(url);
    }

    private void setDistanceUrl(int i) {

        /*
        https://maps.googleapis.com/maps/api/distancematrix/json
  ?destinations=40.659569 * -73.933783 * 40.729029 * -73.851524 * 40.6860072 * -73.6334271 * 40.598566 * -73.7527626
  &origins=40.6655101%2C-73.89188969999998
  &key=YOUR_API_KEY
         */
        //initialize url
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json" +//url
                "?destinations=" + listResultToSave.get(i).get("lat") + "," + listResultToSave.get(i).get("lng") +//location latitude and longitude
                "&origins=" + currentLat + "," + currentLong +//nearby radius
                "&types=" + "pharmacy" +//place type
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

        private Pharmacy pharmacy;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //create json parser
            JsonParser jsonParser = new JsonParser();
            //initialize hash map list
            listResultToSave = new ArrayList<>();

            JSONObject object = null;
            try {
                pharmacies.clear();
                //initialize json object
                object = new JSONObject(strings[0]);
                //parse json object
                Log.i(TAG, "doInBackground: object -> " + object);
                listResultToSave = jsonParser.parseResultToSave(object);
                Log.i(TAG, "doInBackground: listResultToSave -> " + listResultToSave);

                for (int i = 0; i < listResultToSave.size(); i++) {
                    if (listResultToSave.get(i).isEmpty())
                        continue;
                    pharmacy = new Pharmacy(listResultToSave.get(i).get("place_id"),listResultToSave.get(i).get("name"),listResultToSave.get(i).get("open_now"));
                    pharmacies.add(pharmacy);

                }
                Log.i(TAG, "doInBackground: getPharmaciesListToListFragment size -> " + pharmacies.size());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return map list
            return null;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);

            getActivity().runOnUiThread(() -> {
                getPharmaciesListToListFragment(pharmacies);
                recyclerAdapterPharmacy.notifyDataSetChanged();
                loading.setVisibility(View.INVISIBLE);
            });
        }
    }




    private void getPharmaciesListToListFragment(List<Pharmacy> pharmacyListParam) {

        Log.i(TAG, "The tasks list from get task to home page method is -> " + pharmacyListParam.size());

//        if (pharmacyListParam.size()>=1) noTaskToShow.setVisibility(View.GONE);
        Log.i(TAG, "getPharmaciesListToListFragment: pharmacyListParam size -> "+pharmacyListParam.size());
        recyclerAdapterPharmacy = new RecyclerAdapterPharmacy(pharmacyListParam, position -> {
            Intent intent = new Intent(getContext(), PharmacyDetailsActivity.class);
            intent.putExtra("Id", pharmacyListParam.get(position).getId());
            intent.putExtra("IsOpen", pharmacyListParam.get(position).getIsOpen());
            intent.putExtra("userLatitude", currentLat);
            intent.putExtra("userLongitude", currentLong);
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(recyclerAdapterPharmacy);
    }

    private void onRefreshPullListener() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setOnClickListener(){
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MainActivity.class));
        });
    }
}