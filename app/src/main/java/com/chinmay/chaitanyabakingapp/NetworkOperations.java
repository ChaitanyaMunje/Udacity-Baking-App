package com.chinmay.chaitanyabakingapp;

import com.chinmay.chaitanyabakingapp.Interface.OnRequestFinishedListener;
import com.chinmay.chaitanyabakingapp.Interface.RetrofitEndPoint;
import com.chinmay.chaitanyabakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkOperations {

    private static List<Recipe> recipes;
    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static void getRecipes(final OnRequestFinishedListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitEndPoint retrofitEndPoint = retrofit.create(RetrofitEndPoint.class);

        Call<List<Recipe>> call = retrofitEndPoint.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                listener.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
