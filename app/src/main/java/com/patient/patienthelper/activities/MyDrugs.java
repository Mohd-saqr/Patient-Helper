package com.patient.patienthelper.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Drug;
import com.google.gson.Gson;
import com.patient.patienthelper.R;
import com.patient.patienthelper.adapters.RecyclerMyDrugsAdapter;
import com.patient.patienthelper.helperClass.MySharedPreferences;
import com.patient.patienthelper.helperClass.UserLogIn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequiresApi(api = Build.VERSION_CODES.O)
public class MyDrugs extends AppCompatActivity {
    private static final String TAG = "";
    Button add;
    RecyclerMyDrugsAdapter myadapter;
    List<com.amplifyframework.datastore.generated.model.Drug> Drugslist = new ArrayList<>();
    RecyclerView rrecyclerview;
    MySharedPreferences sharedPreferences;
    UserLogIn userLogIn;
    List<LocalDate> ALLDates;
    LocalDate localDaten = LocalDate.now();
    String formattedString;
    DateTimeFormatter formatter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drugs);
        sharedPreferences = new MySharedPreferences(this);
        Gson gson = new Gson();
        userLogIn = gson.fromJson(sharedPreferences.getString("userLog", null), UserLogIn.class);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedString = localDaten.format(formatter);
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


                    Drugslist.clear();
                    if (res.hasData()) {
                        for (com.amplifyframework.datastore.generated.model.Drug drug : res.getData()) {
//                            Log.i(TAG, "fetch: "+drug.getUserId());
//                            Log.i(TAG, "fetch: "+userLogIn.getId());
//                            Log.i(TAG, "fetch: "+localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
//                            Log.i(TAG, "fetch: "+formattedString);
//                            Log.i(TAG, "fetch: "+drug.getData());
                            ALLDates = new ArrayList<>();
                            try {
                                LocalDate localDate = LocalDate.parse(drug.getStartDate());
                                LocalDate localDate1 = LocalDate.parse(drug.getEndDate());

                                ALLDates = getDatesBetween(localDate, localDate1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (drug.getUserId().equals(userLogIn.getId()) && ((containsdate(formattedString, ALLDates)) || drug.getStartDate().equals("ALL") || (drug.getStartDate().equals(LocalDate.now().format(formatter))))) {

                                Drugslist.add(drug);
                            }

                        }
                    }
                    runOnUiThread(() -> myadapter.notifyDataSetChanged());
                }
                , err -> {
                });
    }

    private boolean containsdate(String formattedString, List<LocalDate> listdates) {
        Boolean bool = false;
        for (int i = 0; i < listdates.size(); i++) {
            String s1 = listdates.get(i).toString().trim();
            if (s1.equals(formattedString))
                bool = true;
        }
        return bool;
    }

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }


}