package com.patient.patienthelper.api;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetApi {

    public static Call<List<Disease>> getDisease() throws IOException {

        /**
         * using the  retrofit library to fetch data from api
         *          to use this call just call GetApi.getDisease()
         *          look at lookingForActivity at get api method
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://patientapi2022.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiMethod apiMethod = retrofit.create(ApiMethod.class);
        return apiMethod.listDisease();

    }
    public static Call<List<Disease>> getDrugsName() throws IOException {

        /**
         * using the  retrofit library to fetch data from api
         *          to use this call just call GetApi.getDisease()
         *          look at lookingForActivity at get api method
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://patientapi2022.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiMethod apiMethod = retrofit.create(ApiMethod.class);
        return apiMethod.listDisease();

    }
}
