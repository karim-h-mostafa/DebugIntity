package com.eita.signup.utils.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserPreferences {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    @Inject
    public UserPreferences(@ApplicationContext Context context) {
        preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setAuthenticationToken(String authToken) {
        editor.putString(Constants.AUTH_TOKEN, authToken).apply();
    }

    public String getAuthToken() {
        return preferences.getString(Constants.AUTH_TOKEN, "");
    }


    public boolean isLoggedIn() {
        return !getAuthToken().isEmpty();
    }


}
