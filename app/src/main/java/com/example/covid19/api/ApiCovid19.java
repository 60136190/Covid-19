package com.example.covid19.api;

import com.example.covid19.model.CountryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCovid19 {
    // get all countries
    @GET("countries")
    Call<List<CountryData>> getAllCountry();

    // get data in Vietnam
    @GET("countries/704")
    Call<CountryData> getDataVietNam();

    // get data country
    @GET("countries/{id}")
    Call<CountryData> getDataCountry(@Path("id") int id);
}

