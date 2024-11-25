package com.example.todolistmobile.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.todolistmobile.api.service.UserApiService;
import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.api.util.ApiUtil;
import com.example.todolistmobile.model.AuthResponse;
import com.example.todolistmobile.model.RetroFitClient;
import com.example.todolistmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserRepository {

    private UserApiService userApi;

    public UserRepository() {
        Retrofit retrofit = RetroFitClient.getRetrofitInstance();

        userApi = retrofit.create(UserApiService.class);
    }

    public void getUsers(ApiCallback<List<User>> callback) {
        Call<List<User>> call = userApi.getUsers();
        ApiUtil.executeCall(call, callback);
    }

    public void getUserById(int id, ApiCallback<User> callback) {
        Call<User> call = userApi.getUserById(id);
        ApiUtil.executeCall(call, callback);
    }
}
