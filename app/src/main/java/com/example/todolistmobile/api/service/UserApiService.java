package com.example.todolistmobile.api.service;

import com.example.todolistmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("/user")
    Call<List<User>> getUsers();

    @GET("user/{id}")
    Call<User> getUserById(@Path("id") int userId);
}

