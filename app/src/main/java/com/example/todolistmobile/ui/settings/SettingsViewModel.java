package com.example.todolistmobile.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistmobile.model.User;

import java.util.List;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    private MutableLiveData<List<User>> users;


    public MutableLiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
        }
        return users;
    }

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

        users = getUsers();

        System.out.println(users);

    }

    public LiveData<String> getText() {
        return mText;
    }
}
