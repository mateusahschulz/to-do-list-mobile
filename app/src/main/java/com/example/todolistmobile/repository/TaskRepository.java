package com.example.todolistmobile.repository;

import com.example.todolistmobile.api.service.TaskApiService;
import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.api.util.ApiUtil;
import com.example.todolistmobile.model.Task;
import com.example.todolistmobile.model.RetroFitClient;
import com.example.todolistmobile.model.AppUserManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class TaskRepository {

    private TaskApiService taskApi;

    AppUserManager userManager;

    public TaskRepository() {
        Retrofit retrofit = RetroFitClient.getRetrofitInstance();
        taskApi = retrofit.create(TaskApiService.class);
    }

    public void getTasksByUserId(Integer userId, ApiCallback<List<Task>> callback) {
        Call<List<Task>> call = taskApi.getTasks(userId);
        ApiUtil.executeCall(call, callback);
    }

    public void createTask(Task task, ApiCallback<Task> callback) {
        Call<Task> call = taskApi.createTask(task);
        ApiUtil.executeCall(call, callback);
    }

    public void updateTask(Task task, ApiCallback<Task> callback) {
        Call<Task> call = taskApi.updateTask(task, task.getId());
        ApiUtil.executeCall(call, callback);
    }

    public void updateTaskStatus(Task task, ApiCallback<Task> callback) {
        Call<Task> call = taskApi.updateTaskStatus(task.getId(), task.getStatus());
        ApiUtil.executeCall(call, callback);
    }

    public void deleteTask(Integer taskId, ApiCallback<Task> callback) {
        Call<Task> call = taskApi.deleteTask(taskId);
        ApiUtil.executeCall(call, callback);
    }
}
