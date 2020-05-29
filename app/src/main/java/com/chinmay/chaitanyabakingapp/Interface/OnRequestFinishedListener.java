package com.chinmay.chaitanyabakingapp.Interface;

import com.chinmay.chaitanyabakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Response;

public interface OnRequestFinishedListener {
    void onFailure();

    void onResponse(Response<List<Recipe>> response);
}
