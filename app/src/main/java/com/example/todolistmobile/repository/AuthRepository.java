package com.example.todolistmobile.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.todolistmobile.api.service.AuthApiService;
import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.api.util.ApiUtil;
import com.example.todolistmobile.model.AuthResponse;
import com.example.todolistmobile.model.RetroFitClient;
import com.example.todolistmobile.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AuthRepository {

    private AuthApiService authApi;

    public AuthRepository() {
        Retrofit retrofit = RetroFitClient.getRetrofitInstance();

        authApi = retrofit.create(AuthApiService.class);
    }

    public void login(User user, ApiCallback<User> callback) { // TODO antes tava AuthReponse
        Call<User> call = authApi.login(user);
        ApiUtil.executeCall(call, callback);
    }

}
