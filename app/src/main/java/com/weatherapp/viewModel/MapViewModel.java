package com.weatherapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.weatherapp.model.Weather;
import com.weatherapp.repository.WeatherDataSource;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {
    private WeatherDataSource weatherDataSource;
    private MutableLiveData<ArrayList<Weather>> weatherListMutableData = new MutableLiveData<>();
    private MutableLiveData<Weather> weatherMutableData = new MutableLiveData<>();
    private MutableLiveData<Boolean> showWeatherInformation = new MutableLiveData<>();

    public MapViewModel(WeatherDataSource dataSource) {
        weatherDataSource = dataSource;
    }

    public LiveData<ArrayList<Weather>> getWeatherListLiveData() {
        return weatherListMutableData;
    }
    public LiveData<Weather> getWeatherLiveData() {
        return weatherMutableData;
    }

    public LiveData<Boolean> showWeatherLiveData() {
        return showWeatherInformation;
    }

    public void getWeatherByLocation(double lat, double lng) {
        weatherDataSource.getWeatherByLocation(lat, lng, weatherMutableData, showWeatherInformation);
    }
}
