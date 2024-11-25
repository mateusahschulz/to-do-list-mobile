package com.example.todolistmobile.api.service;

import com.example.todolistmobile.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskApiService {
    @GET("/task/by-user/{id}") // Endpoint da API
    Call<List<Task>> getTasks(@Path("id") int userId);

    // @GET("/task")
    // Call<List<Task>> getTasks();

    @POST("/task")
    Call<Task> createTask(@Body Task task);

    @PUT("/task/{id}")
    Call<Task> updateTask(@Body Task task, @Path("id") Integer taskId);

    @PATCH("task/{id}/status")
    Call<Task> updateTaskStatus(@Path("id") int taskId, @Query("status") String status);

    @DELETE("/task/{id}")
    Call<Task> deleteTask(@Path("id") Integer taskId);
}
