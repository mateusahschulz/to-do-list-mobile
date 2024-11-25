package com.example.todolistmobile.api.service;

import com.example.todolistmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("/user") // Endpoint da API
    Call<List<User>> getUsers();

    @GET("user/{id}") // Endpoint com parâmetro
    Call<User> getUserById(@Path("id") int userId);
}

