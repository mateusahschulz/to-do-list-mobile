package com.example.todolistmobile.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolistmobile.api.util.ApiCallback;
import com.example.todolistmobile.model.User;
import com.example.todolistmobile.model.AppUserManager;
import com.example.todolistmobile.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {

    private AppUserManager userManager;
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<String> loginError;
    private AuthRepository authRepository;


    public LoginViewModel(Application application) {
        super(application);
        userManager = new AppUserManager(application);
        isLoggedIn = new MutableLiveData<Boolean>();
        loginError = new MutableLiveData<String>();
        authRepository = new AuthRepository();
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        isLoggedIn.setValue(userManager.isUserLoggedIn());
    }

    public LiveData<Boolean> isLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }


    public void login(String userName, String password) {
        User loginRequest = new User();
        loginRequest.setUserName(userName);
        loginRequest.setPassword(password);

        authRepository.login(loginRequest, new ApiCallback<User>() {
            @Override
            public void onSuccess(User result) {
                System.out.println(result);
                User userAuth = result;
                userManager.saveUser(userAuth.getUserName(), userAuth.getId());
                isLoggedIn.setValue(true);
            }

            @Override
            public void onError(String error) {
                loginError.setValue(error);
            }
        });
    }


    public void logout() {
        userManager.logout();
        isLoggedIn.setValue(false);
    }
}
