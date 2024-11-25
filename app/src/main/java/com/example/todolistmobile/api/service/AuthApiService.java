package com.example.todolistmobile.api.service;

import com.example.todolistmobile.model.AuthResponse;
import com.example.todolistmobile.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("/auth/login")
    Call<User> login(@Body User user);
} // TODO tava auth
