package com.weatherapp;

import android.app.Application;

import com.weatherapp.utils.ApplicationManager;

public class BaseApp extends Application {

    private static ApplicationManager applicationManager;

    public static ApplicationManager getApplicationManager() {
        if (applicationManager == null) {
            applicationManager = new ApplicationManager();
        }
        return applicationManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationManager = new ApplicationManager();
        applicationManager.setContext(this);
    }
}
