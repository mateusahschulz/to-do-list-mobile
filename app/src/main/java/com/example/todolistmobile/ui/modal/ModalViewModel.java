package com.example.todolistmobile.ui.modal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.model.Task;
import com.example.todolistmobile.model.AppUserManager;
import com.example.todolistmobile.repository.TaskRepository;

public class ModalViewModel extends ViewModel {
    private final MutableLiveData<String> inputText;
    private final MutableLiveData<Boolean> closeEvent;
    private MutableLiveData<Boolean> isTaskCreated;
    private MutableLiveData<String> taskCreationError;
    private TaskRepository taskRepository;
    private AppUserManager userManager;


    public ModalViewModel(AppUserManager userManager) {
        inputText = new MutableLiveData<>();
        closeEvent = new MutableLiveData<>();
        isTaskCreated = new MutableLiveData<>();
        taskCreationError = new MutableLiveData<>();
        taskRepository = new TaskRepository();
        this.userManager = userManager;
    }

    public LiveData<String> getInputText() {
        return inputText;
    }

    public void setInputText(String text) {
        inputText.setValue(text);
    }

    public LiveData<Boolean> getCloseEvent() {
        return closeEvent;
    }

    public void closeModal() {
        closeEvent.setValue(true);
    }

    public void resetCloseEvent() {
        closeEvent.setValue(false);
    }

    public LiveData<Boolean> getIsTaskCreated() {
        return isTaskCreated;
    }

    public LiveData<String> getTaskCreationError() {
        return taskCreationError;
    }

    public void createTask(String title, String description, String dueDate) {
        Task taskToBeCreated = new Task(-1, title, description, dueDate, userManager.getUserId(), "todo" );
        taskRepository.createTask(taskToBeCreated, new ApiCallback<Task>() {
            @Override
            public void onSuccess(Task result) {
                // A tarefa foi criada com sucesso
                isTaskCreated.setValue(true);
            }

            @Override
            public void onError(String error) {
                // Houve um erro ao criar a tarefa
                taskCreationError.setValue(error);
            }
        });
    }
}
