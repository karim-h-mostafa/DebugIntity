package com.eita.core.di.application;

import android.app.Application;


import com.airbnb.lottie.BuildConfig;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;


@HiltAndroidApp
public class DebugintityApp extends Application {
    @Inject
    Timber.DebugTree debugTree;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(debugTree);
        }

    }
}
