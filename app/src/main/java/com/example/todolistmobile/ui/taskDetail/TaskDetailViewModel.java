package com.example.todolistmobile.ui.taskDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.model.Task;
import com.example.todolistmobile.repository.TaskRepository;

public class TaskDetailViewModel extends ViewModel {

    private Task task;
    private TaskRepository taskRepository;
    private MutableLiveData<Boolean> isTaskUpdated;
    private MutableLiveData<String> taskUpdateError;
    private MutableLiveData<Boolean> isTaskDeleted;
    private MutableLiveData<String> taskDeleteError;
    private MutableLiveData<Boolean> isTaskStatusUpdated;
    private MutableLiveData<String> taskUpdateStatusError;

    // Método para setar a tarefa

    public TaskDetailViewModel() {
        taskRepository = new TaskRepository();
        isTaskUpdated = new MutableLiveData<Boolean>();
        taskUpdateError = new MutableLiveData<String>();
        isTaskDeleted = new MutableLiveData<Boolean>();
        taskDeleteError = new MutableLiveData<String>();
        isTaskStatusUpdated = new MutableLiveData<Boolean>();
        taskUpdateStatusError = new MutableLiveData<String>();
    }
    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public LiveData<Boolean> getIsTaskUpdated() {
        return isTaskUpdated;
    }

    public LiveData<Boolean> getIsTaskDeleted() {
        return isTaskDeleted;
    }

    public LiveData<String> getTaskDeleteError() {
        return taskDeleteError;
    }

    public LiveData<String> getTaskUpdateError() {
        return taskUpdateError;
    }

    public LiveData<String> getTaskUpdateStatusError() {
        return taskUpdateStatusError;
    }

    public LiveData<Boolean> getIsTaskStatusUpdated() {
        return isTaskStatusUpdated;
    }

    public void updateTask(Task taskToBeUpdated) {
        // Simulação de chamada de API para atualizar a tarefa
        taskRepository.updateTask(taskToBeUpdated, new ApiCallback<Task>() {
            @Override
            public void onSuccess(Task result) {
                isTaskUpdated.setValue(true);
            }

            @Override
            public void onError(String error) {
                taskUpdateError.setValue(error);
            }
        });
    }

    public void updateTaskStatus(Task task) {
        taskRepository.updateTaskStatus(task, new ApiCallback<Task>() {
            @Override
            public void onSuccess(Task result) {
                isTaskStatusUpdated.setValue(true);
            }

            @Override
            public void onError(String error) {
                taskUpdateStatusError.setValue(error);
            }
        });
    }

    public void deleteTask(Integer taskId) {
        taskRepository.deleteTask(taskId, new ApiCallback<Task>() {
            @Override
            public void onSuccess(Task result) {
                isTaskDeleted.setValue(true);
            }

            @Override
            public void onError(String error) {
                taskDeleteError.setValue(error);
            }
        });
    }
}
