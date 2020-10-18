package com.weatherapp.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.weatherapp.BaseApp;
import com.weatherapp.BuildConfig;
import com.weatherapp.api.WeatherApi;
import com.weatherapp.model.Weather;
import com.weatherapp.utils.ActivityManager;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherDataSource {

    private WeatherApi weatherApi;
    private WeatherLocalDataSource localDataSource;
    private ActivityManager activityManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Weather> currentMutableLiveData;

    public WeatherDataSource(WeatherApi api, WeatherLocalDataSource dataSource, ActivityManager manager) {
        weatherApi = api;
        localDataSource = dataSource;
        activityManager = manager;
    }

    @SuppressLint("CheckResult")
    public void getWeatherWeek(String city, MutableLiveData<ArrayList<Weather>> weatherListLD) {
        weatherApi.getWeatherWeek(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Weather>>() {
                    @Override
                    public void onSuccess(ArrayList<Weather> weatherResponse) {
                        if (weatherResponse != null) {
                            weatherListLD.setValue(weatherResponse);
                        } else {
                            weatherListLD.setValue(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getWeatherByLocation(double lat, double lng, MutableLiveData<Weather> weatherListLD, MutableLiveData<Boolean> showWeatherInformation) {
        currentMutableLiveData = weatherListLD;
        BaseApp.getApplicationManager().getActivityManager().showLoader();
        weatherApi.getWeatherByLocation(lat, lng, BuildConfig.API_KEY, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Weather>() {
                    @Override
                    public void onSuccess(Weather weatherResponse) {
                        if (weatherResponse != null) {
                            weatherListLD.setValue(weatherResponse);
                        } else {
                            weatherListLD.setValue(new Weather());
                        }
                        showWeatherInformation.setValue(false);
                        BaseApp.getApplicationManager().getActivityManager().hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        BaseApp.getApplicationManager().getActivityManager().hideLoader();
                    }
                });
    }

    public void getAllBookmarkDB(MutableLiveData<ArrayList<Weather>> weatherListMutableData){
        weatherListMutableData.setValue(localDataSource.runGetAllWeatherQuery());
    }

    public void getBookmarkByIdDB(Weather weather){
        localDataSource.runGetWeatherQuery(weather.getId());
    }

    public void saveBookmarkDB(Weather weather){
        Gson gson = new Gson();
        String values =  String.valueOf(weather.getId()) + ",'" + gson.toJson(weather) + "'";
        localDataSource.runDeleteWeatherQuery(weather.getId());
        localDataSource.runCreateWeatherQuery(values);
    }

    public void deleteBookmarkDB(Weather weather){
        localDataSource.runDeleteWeatherQuery(weather.getId());
    }
}
