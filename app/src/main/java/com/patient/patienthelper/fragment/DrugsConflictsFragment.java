package com.patient.patienthelper.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Conflicts;
import com.patient.patienthelper.api.GetApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.ionbit.ionalert.IonAlert;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DrugsConflictsFragment extends Fragment {
    MaterialButton check;
    final List<Conflicts> ConflictsListApi = new ArrayList<>();
    ArrayList<String> DrugsConflicts = new ArrayList<>();
    ArrayList<String> Drugs = new ArrayList<>();
    TextView textview;
    TextView textview1;
    String d1;
    String d2;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1;
    ArrayAdapter<String> spinnerArrayAdapter1;
    ArrayAdapter<String> spinnerArrayAdapter;

    Dialog dialog;

    public DrugsConflictsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugs_conflicts, container, false);
        spinnerArrayAdapter1= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        try {
            addItemsOnSpinner1();
            addItemsOnSpinner();

        } catch (IOException e) {
            e.printStackTrace();
        }
        check = view.findViewById(R.id.Check);
        check.setOnClickListener(v -> toast(checkconflict()));

        // assign variable
        textview = view.findViewById(R.id.testView);
        textview1 = view.findViewById(R.id.testView1);

        // initialize array list
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        arrayList.addAll(Drugs);
        arrayList1.addAll(DrugsConflicts);


        textview.setOnClickListener(v -> {
            // Initialize dialog
            dialog = new Dialog(getContext());

            // set custom dialog
            dialog.setContentView(R.layout.dialog_searchable_spinner);

            // set custom height and width
            dialog.getWindow().setLayout(650, 800);

            // set transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog
            dialog.show();

            // Initialize and assign variable
            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);

            // Initialize array adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);

            // set adapter
            listView.setAdapter(adapter);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                // when item selected from list
                // set selected item on textView
                textview.setText(adapter.getItem(position));

                // Dismiss dialog
                dialog.dismiss();
            });
        });


        textview1.setOnClickListener(v -> {
            // Initialize dialog
            dialog = new Dialog(getContext());

            // set custom dialog
            dialog.setContentView(R.layout.dialog_searchable_spinner);

            // set custom height and width
            dialog.getWindow().setLayout(650, 800);

            // set transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog
            dialog.show();

            // Initialize and assign variable
            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);

            // Initialize array adapter
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList1);

            // set adapter
            listView.setAdapter(adapter1);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter1.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener((parent, view12, position, id) -> {
                // when item selected from list
                // set selected item on textView
                textview1.setText(adapter1.getItem(position));

                // Dismiss dialog
                dialog.dismiss();
            });
        });
        return view;
    }

    public void addItemsOnSpinner1() throws IOException {
        GetApi.getConflicts().enqueue(new Callback<List<Conflicts>>() {
            private static final String TAG = "";

            @Override
            public void onResponse(Call<List<Conflicts>> call, Response<List<Conflicts>> response) {
                ConflictsListApi.addAll(response.body());
                Log.i(TAG, "API: " + ConflictsListApi.get(0).toString());

                if (ConflictsListApi.size() > 0) {
                    for (int i = 0; i < ConflictsListApi.size(); i++) {
                        arrayList.add(ConflictsListApi.get(i).getDrug());
                    }

                    spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerArrayAdapter1.addAll(Drugs);
//                    D1.setAdapter(spinnerArrayAdapter1);

                }
            }

            @Override
            public void onFailure(Call<List<Conflicts>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addItemsOnSpinner() throws IOException {
        GetApi.getConflicts().enqueue(new Callback<List<Conflicts>>() {
            private static final String TAG = "";

            @Override
            public void onResponse(Call<List<Conflicts>> call, Response<List<Conflicts>> response) {
                ConflictsListApi.addAll(response.body());
                Log.i(TAG, "API: " + ConflictsListApi.get(0).toString());

                if (ConflictsListApi.size() > 0) {
                    for (int i = 0; i < ConflictsListApi.size(); i++) {
                        for (int j = 0; j < ConflictsListApi.get(i).getConflicts_drugs().size(); j++) {
                            arrayList1.add(ConflictsListApi.get(i).getConflicts_drugs().get(j));
                        }

                    }

                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerArrayAdapter.addAll(DrugsConflicts);
//                    D2.setAdapter(spinnerArrayAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Conflicts>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean checkconflict() {
        d1 = textview.getText().toString();
        d2 = textview1.getText().toString();
        boolean bool = false;
        for (int i = 0; i < ConflictsListApi.size(); i++) {
            if (ConflictsListApi.get(i).getDrug().equals(d1)) {
                for (int j = 0; j < ConflictsListApi.get(i).getConflicts_drugs().size(); j++) {
                    if (ConflictsListApi.get(i).getConflicts_drugs().get(j).equals(d2))
                        bool = true;
                }
            }
        }
        return bool;

    }

    public void toast(boolean bool) {
        if (bool) {

            /// motion toast

//            MotionToast.Companion.createToast(getActivity(),
//                    "interaction",
//                    "There is interaction between these two drugs!",
//                    MotionToastStyle.WARNING,
//                    MotionToast.GRAVITY_BOTTOM,
//                    MotionToast.LONG_DURATION,
//                    ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.montserrat_regular));

            IonAlert ionAlert = new IonAlert(getActivity(),IonAlert.ERROR_TYPE);


            ionAlert.setTitleText("interaction Found");
            ionAlert.setContentText("There is interaction between these two drugs! " +
                    "Please don't use any of these Drugs with each other and ask the doctor " +
                    "to find alternative drugs");
            ionAlert.setContentTextSize(18);

            ionAlert.setConfirmText("Find doctors");
            ionAlert.setCanceledOnTouchOutside(true);
            ionAlert.setTitleText("Interaction");
            ionAlert.setConfirmText("Find doctors");
            ionAlert.show();

//             ionAlert     new IonAlert(getActivity(), IonAlert.WARNING_TYPE)
//
//                    .setTitleText("interaction Found")
//
//                    .setContentText("There is interaction between these two drugs! " +
//                            "Please don't use any one of Drugs with another and ask the doctor " +
//                            "to find alternative drugs")
//                    .setConfirmText("Find doctors")
//                    .setCancelText("Close")
//
//                    .setConfirmClickListener(new IonAlert.ClickListener() {
//                        @Override
//                        public void onClick(IonAlert ionAlert) {
//
//                        }
//                    })
//
//
//                    .show();


//
//            Toast toast = Toast.makeText(getContext(),
//                    "There is interaction between these two drugs",
//                    Toast.LENGTH_SHORT);
////            View toastView = toast.getView();
//            toast.show();
        } else {
//            Toast toast = Toast.makeText(getContext(),
//                    "There is no interaction , Its fine",
//                    Toast.LENGTH_SHORT);
////            View toastView = toast.getView();
//            toast.show();

//
            new IonAlert(getActivity(), IonAlert.SUCCESS_TYPE)

                    .setTitleText("No interaction Found")
                    .setContentText("There is no interaction , Its fine")
                    .setConfirmText("Ok")
                    .showCancelButton(false)

                    .setConfirmClickListener(new IonAlert.ClickListener() {
                        @Override
                        public void onClick(IonAlert ionAlert) {
                            ionAlert.dismiss();
                        }
                    })
                    .show();
        }

    }

}



