package com.patient.patienthelper.fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.patient.patienthelper.R;
import com.patient.patienthelper.api.Conflicts;
import com.patient.patienthelper.api.GetApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrugsConflictsFragment extends Fragment {

    private static final String TAG = "";
    private Button check;
    private Spinner D1;
    private Spinner D2;
    private final List<Conflicts> ConflictsListApi = new ArrayList<>();
    ArrayList<String> DrugsConflicts = new ArrayList<>();
    ArrayList<String> Drugs = new ArrayList<>();

    String d1;
    String d2;

    public DrugsConflictsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugs_conflicts, container, false);
        findViews(view);

        try {
            addItemsOnSpinner();
            addItemsOnSpinner1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        check.setOnClickListener(v -> toast(checkconflict()));
        return view;
    }

    private void findViews(View view) {
        check = (Button) view.findViewById(R.id.Check);
        D1 = view.findViewById(R.id.Drug1);
        D2 = view.findViewById(R.id.Drug2);
    }

    public void addItemsOnSpinner1() throws IOException {
        GetApi.getConflicts().enqueue(new Callback<List<Conflicts>>() {
            private static final String TAG = "";
            @Override
            public void onResponse(Call<List<Conflicts>> call, Response<List<Conflicts>> response) {
                ConflictsListApi.addAll(response.body());
                Log.i(TAG, "API: " + ConflictsListApi.get(0).toString());

                if (ConflictsListApi != null && ConflictsListApi.size() > 0) {
                    for (int i = 0; i < ConflictsListApi.size(); i++) {
                        Drugs.add(ConflictsListApi.get(i).getDrug());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
                    spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerArrayAdapter1.addAll(Drugs);
                    D1.setAdapter(spinnerArrayAdapter1);

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

                if (ConflictsListApi != null && ConflictsListApi.size() > 0) {
                    for (int i = 0; i < ConflictsListApi.size(); i++) {
                        for (int j = 0; j < ConflictsListApi.get(i).getConflicts_drugs().size(); j++) {
                            DrugsConflicts.add(ConflictsListApi.get(i).getConflicts_drugs().get(j));
                        }

                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerArrayAdapter.addAll(DrugsConflicts);
                    D2.setAdapter(spinnerArrayAdapter);

                }
            }
            @Override
            public void onFailure(Call<List<Conflicts>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean checkconflict() {
        d1 = String.valueOf(D1.getSelectedItem());
        d2 = String.valueOf(D2.getSelectedItem());
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
        if (bool == true) {

            Toast toast = Toast.makeText(getContext(),
                    "There is interaction between these two drugs",
                    Toast.LENGTH_SHORT);
            View toastView = toast.getView();

//  //          toastView.setBackgroundResource(R.drawable.toast_drawable);

       //     toastView.setBackgroundResource(R.drawable.toast_drawable);

            toast.show();
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "There is no interaction , Its fine",
                    Toast.LENGTH_SHORT);
            View toastView = toast.getView();

//  //          toastView.setBackgroundResource(R.drawable.toast_drawablea);

   ///         toastView.setBackgroundResource(R.drawable.toast_drawablea);

            toast.show();

        }

    }


}



