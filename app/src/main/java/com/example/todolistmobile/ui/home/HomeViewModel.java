package com.example.todolistmobile.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.model.AppUserManager;
import com.example.todolistmobile.model.Task;
import com.example.todolistmobile.repository.TaskRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> taskList = new MutableLiveData<>();
    private final TaskRepository taskRepository;
    private final AppUserManager appUserManager;

    public HomeViewModel(Application application) {
        taskRepository = new TaskRepository();
        appUserManager = new AppUserManager(application.getApplicationContext());
        fetchCards();
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public void fetchCards() {

        taskRepository.getTasksByUserId(appUserManager.getUserId(), new ApiCallback<List<Task>>() {
            @Override
            public void onSuccess(List<Task> result) {
                taskList.setValue(result);
            }

            @Override
            public void onError(String error) {
                System.err.println("Erro ao buscar cards: " + error);
            }
        });
    }
}