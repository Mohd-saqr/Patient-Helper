package com.patient.patienthelper.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.patient.patienthelper.R;
import com.patient.patienthelper.activitys.MainActivity;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button findDrug;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findAllViewById(view);
        setOnClickListener();
        // Inflate the layout for this fragment
        return view;

    }

    private void findAllViewById(View view){
        findDrug = (Button) view.findViewById(R.id.find_drug_button);

    }
    private void setOnClickListener(){
        findDrug.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Find drug", Toast.LENGTH_SHORT).show();
        });
    }
}