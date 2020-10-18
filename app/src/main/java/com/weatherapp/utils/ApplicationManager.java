package com.weatherapp.utils;

import android.content.Context;

import com.weatherapp.BuildConfig;

import com.weatherapp.api.WeatherApi;
import com.weatherapp.repository.WeatherDataSource;
import com.weatherapp.repository.WeatherLocalDataSource;
import com.weatherapp.repository.database.DatabaseUtils;
import com.weatherapp.ui.MapsFragment;
import com.weatherapp.ui.home.WeatherSearchFragment;
import com.weatherapp.viewModel.MapViewModel;
import com.weatherapp.viewModel.WeatherSearchViewModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationManager {

    private Retrofit retrofit;
    private Context currentContext;
    private ActivityManager activityManager;
    private DatabaseUtils databaseUtils;
    private WeatherDataSource weatherDataSource;
    private WeatherLocalDataSource localDataSource;
    private WeatherSearchViewModel WeatherSearchViewModel;
    public WeatherSearchFragment weatherSearchFragment;
    public MapsFragment mapsFragment;
    private MapViewModel mapViewModel;

    public void setContext(Context context) {
        currentContext = context;
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public ApplicationManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        activityManager = new ActivityManager();
        databaseUtils = new DatabaseUtils(this);
        localDataSource = new WeatherLocalDataSource(databaseUtils, activityManager);
        weatherDataSource = new WeatherDataSource(retrofit.create(WeatherApi.class), localDataSource, activityManager);
        WeatherSearchViewModel = new WeatherSearchViewModel(weatherDataSource);
        weatherSearchFragment = new WeatherSearchFragment(WeatherSearchViewModel, this);
        mapViewModel = new MapViewModel(weatherDataSource);
        mapsFragment = new MapsFragment(mapViewModel);
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }
}
