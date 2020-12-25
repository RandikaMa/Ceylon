package com.example.ceylon.rest;

import com.example.ceylon.model.Food;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("raw/cTZvdk3N")
    abstract public Call<Food> getFoods();
}