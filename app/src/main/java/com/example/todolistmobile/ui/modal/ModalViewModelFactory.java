package com.example.todolistmobile.ui.modal;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistmobile.model.AppUserManager;

public class ModalViewModelFactory implements ViewModelProvider.Factory {
    private final AppUserManager userManager;

    public ModalViewModelFactory(AppUserManager userManager) {
        this.userManager = userManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ModalViewModel.class)) {
            return (T) new ModalViewModel(userManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}