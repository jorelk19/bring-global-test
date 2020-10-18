package com.weatherapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.weatherapp.model.Weather;
import com.weatherapp.repository.WeatherDataSource;

import java.util.ArrayList;

public class WeatherSearchViewModel extends ViewModel  {
    private WeatherDataSource weatherDataSource;
    private MutableLiveData<Weather> weatherMutableData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Weather>> weatherListMutableData = new MutableLiveData<>();
    private MutableLiveData<Boolean> showWeatherInformation = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteWeatherInformation = new MutableLiveData<>();

    public WeatherSearchViewModel(WeatherDataSource dataSource) {
        weatherDataSource = dataSource;
    }

    public LiveData<Weather> getWeatherLiveData(){
        return weatherMutableData;
    }

    public LiveData<ArrayList<Weather>> getWeatherListLiveData(){
        return weatherListMutableData;
    }
    public LiveData<Boolean> showWeatherInformationLiveData(){
        return showWeatherInformation;
    }
    public LiveData<Boolean> deleteWeatherInformationLiveData(){
        return deleteWeatherInformation;
    }
    public void saveBookmark(Weather weather){
        weatherDataSource.saveBookmarkDB(weather);
    }

    public void getAllBookmarksDB(){
        weatherDataSource.getAllBookmarkDB(weatherListMutableData);
    }
}
