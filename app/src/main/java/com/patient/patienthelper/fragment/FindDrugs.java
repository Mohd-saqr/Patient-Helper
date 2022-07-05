package com.patient.patienthelper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activities.ShowInfoActivity;
import com.patient.patienthelper.adapters.DrugRecyclerAdapter;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.helperClass.HashTable.HashTable;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.util.ArrayList;
import java.util.List;


public class FindDrugs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    List<Disease> apiData = new ArrayList<>();

    RecyclerView recyclerView;
    DrugRecyclerAdapter recyclerAdapterForDrugs;
    ProgressBar progressBarForDrugs;
    List<String> drugs;
    Button find_pharmcy;
    HashTable hashTable = new HashTable<>(20);
    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;



    public FindDrugs() {

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_drugs, container, false);
        sharedPreferences = new MySharedPreferences(getContext());
        findViewById(view);

//        progressBarForDrugs.setVisibility(View.VISIBLE);


        filter();
        setAdapter();
        setClick();
        return view;
    }

    private void setClick() {
        find_pharmcy.setOnClickListener( v->{
            Fragment fragment;
            fragment = new NearbyPharmaciesListViewFragment();

            FragmentManager fragmentManager = getFragmentManager(); // For AppCompat use getSupportFragmentManager
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_fragment, fragment)
                    .commit();
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void filter() {
        String userDisease = getDiseaseName();
        Disease disease = (Disease) hashTable.get(userDisease);
        drugs = disease.getDrugs_names();
    }

    private void findViewById(View view) {
        recyclerView = view.findViewById(R.id.DrugRecyclerView);
        find_pharmcy=view.findViewById(R.id.find_pharmcy);
    }

    private void setAdapter() {

        recyclerAdapterForDrugs = new DrugRecyclerAdapter(drugs, disease -> {
            Intent intent = new Intent(getContext(), ShowInfoActivity.class);
            intent.putExtra("drugName", disease);
            startActivity(intent);
        }, getActivity());
        recyclerView.setAdapter(recyclerAdapterForDrugs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {

        });
    }
    private String getDiseaseName() {
        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
        hashTable = gson.fromJson(sharedPreferences.getString("ApiData", null), HashTable.class);
        return userLogIn.getDiseaseName();
    }
}