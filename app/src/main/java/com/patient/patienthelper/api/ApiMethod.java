package com.patient.patienthelper.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMethod {

    ///https://patientapi2022.herokuapp.com/api/v1
    @GET("api/v1")
    Call<List<Disease>> listDisease();

    //https://patientapi2022.herokuapp.com/api/advice
    @GET("api/advice")
    Call<List<Advice>> listAdvice();

    // https://patientapi2022.herokuapp.com/api/con
    @GET("api/con")
    Call<List<Conflicts>> listConflicts();

    // https://patientapi2022.herokuapp.com/api/drugs
    @GET("api/drugs")
    Call<List<DrugDetails>> listDrugsDetails();
}
