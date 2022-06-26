package com.patient.patienthelper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.activitys.DrugActivity;
import com.patient.patienthelper.activitys.MainActivity;
import com.patient.patienthelper.api.Advice;
import com.patient.patienthelper.api.Disease;
import com.patient.patienthelper.api.GetApi;
import com.patient.patienthelper.helperClass.MySharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button findDrug;
    private TextView todayAdvice;
    private TextView desName;
    private final List<Advice> adviceListApi = new ArrayList<>();
    private ProgressBar adviceLoading;
    private MySharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences= new MySharedPreferences(getContext());

        findAllViewById(view);
        fetchDataFromApi();
        checkStatus();


        setOnClickListener();

        // Inflate the layout for this fragment
        return view;
    }

    private void updateUri(String checkStatus) {
        if (checkStatus.equals("Patient")){
            desName.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
          Disease disease=  gson.fromJson(getDesisName(),Disease.class);

            todayAdvice.setText(disease.getDescription_t());

            desName.setText(disease.getDisease_name());
        }else {
            getDesisName();
            desName.setVisibility(View.INVISIBLE);
            getAdviceToHomePage(adviceListApi);

        }
    }

    private void fetchDataFromApi(){
        showProgressBar();
        try {
            GetApi.getAdvice().enqueue(new Callback<List<Advice>>() {
                @Override
                public void onResponse(@NonNull Call<List<Advice>> call, @NonNull Response<List<Advice>> response) {
                    assert response.body() != null;
                    adviceListApi.addAll(response.body());
                    updateUri(checkStatus());

                    Log.i("Main Activity", "the advices list size is from onResponse -> "+adviceListApi.size());
                    hideProgressBar();
                }
                @Override
                public void onFailure(@NonNull Call<List<Advice>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Fetch data Failed", Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                    onResume();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }

    private void findAllViewById(View view){
        desName=view.findViewById(R.id.desise_title);
        findDrug = view.findViewById(R.id.find_drug_button);
        todayAdvice = view.findViewById(R.id.text_advice);
        adviceLoading = view.findViewById(R.id.advice_loading_progress);
    }

    private void showProgressBar(){
        adviceLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        adviceLoading.setVisibility(View.GONE);
    }

    private void setOnClickListener(){
        findDrug.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), DrugActivity.class);
            startActivity(i);
        });
    }

    private void getAdviceToHomePage(List<Advice> advice){

        int random = randomString();
        //this check to ensure that the random integer is index of the list to prevent the null result
        Log.i("Main Activity", "the advices list size is -> "+adviceListApi.size());

        if (random < 49 && random >=0)
            todayAdvice.setText(advice.get(random).getAdvice());
        else fetchDataFromApi();
    }

    private int randomString(){
        return (int)(Math.random() * (49) + 1) ;
    }

    private String checkStatus(){

        return sharedPreferences.getString("userStatus","another");
    }
    private String getDesisName(){
        return sharedPreferences.getString("userDisease","another");
    }
}