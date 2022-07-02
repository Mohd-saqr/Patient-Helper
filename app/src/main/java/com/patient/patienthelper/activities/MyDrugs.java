package com.patient.patienthelper.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerMyDrugsAdapter;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MyDrugs extends AppCompatActivity {
    private static final String TAG = "";
    Button add;
    RecyclerMyDrugsAdapter myadapter;
    List<com.amplifyframework.datastore.generated.model.Drug> Drugslist = new ArrayList<>();
    RecyclerView rrecyclerview;
    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;
//    String  formattedDate;
    LocalDate localDate = LocalDate.now();
    String formattedString;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drugs);
        sharedPreferences = new MySharedPreferences(this);

        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
//          formattedDate = localDate.format(DateTimeFormatter
//                .ofLocalizedDate(FormatStyle.SHORT));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LL/dd/yy");
        formattedString = localDate.format(formatter);
        add = findViewById(R.id.addingDrug);
        rrecyclerview = findViewById(R.id.MyDrugsRecycle);
        onclick();
        SetAdapter();
    }


    private void onclick() {
        add.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddDrug.class);
            view.getContext().startActivity(intent);
        });
    }

    private void navigateToMyDrugsPage() {
        Intent intent = new Intent(getApplicationContext(), AddDrug.class);
        startActivity(intent);
    }


    private void SetAdapter() {
        myadapter = new RecyclerMyDrugsAdapter(Drugslist, drug -> {
        });
        rrecyclerview.setAdapter(myadapter);
        rrecyclerview.setHasFixedSize(true);
        rrecyclerview.setLayoutManager(new
                LinearLayoutManager(this));
    }

    private void fetch() {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Drug.class), res ->
                {
                    Log.i(TAG, "fetch:new " + res);
                    Drugslist.clear();
                    if (res.hasData()) {
                        for (com.amplifyframework.datastore.generated.model.Drug drug : res.getData()) {
//                            Log.i(TAG, "fetch: "+drug.getUserId());
//                            Log.i(TAG, "fetch: "+userLogIn.getId());
//                            Log.i(TAG, "fetch: "+localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
//                            Log.i(TAG, "fetch: "+formattedString);
//                            Log.i(TAG, "fetch: "+drug.getData());
                            if (drug.getUserId().equals(userLogIn.getId()) && formattedString.equals(drug.getData()))
                                Drugslist.add(drug);
                        }
                    }
                    runOnUiThread(() -> myadapter.notifyDataSetChanged());
                }
                , err -> {
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }
}