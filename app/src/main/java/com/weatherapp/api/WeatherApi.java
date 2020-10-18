package com.weatherapp.api;

import com.weatherapp.BuildConfig;
import com.weatherapp.model.Weather;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("/data/2.5/forecast?q={city}}&appid=" + BuildConfig.API_KEY)
    Single<ArrayList<Weather>> getWeatherWeek(@Path("city") String city);

    @GET("/data/2.5/weather")
    Single<Weather> getWeatherByLocation(@Query("lat") double lat, @Query("lon") double lng, @Query("appid") String apiKey, @Query("units") String units);
}
