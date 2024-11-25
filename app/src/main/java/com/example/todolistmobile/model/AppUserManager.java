package com.example.todolistmobile.model;

import android.content.Context;
import android.content.SharedPreferences;

public class AppUserManager {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";

    public AppUserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String userName, Integer userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", userName);
        editor.putInt("id", userId);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString("username", null);
    }

    public Integer getUserId() {
        return sharedPreferences.getInt("id", -1);
    }

    public boolean isUserLoggedIn() {
        return getUserId() != -1;
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("id");
        editor.remove("username");
        editor.apply();
    }
}
