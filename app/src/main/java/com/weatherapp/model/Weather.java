package com.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Weather {
    @SerializedName("dt")
    private long id;
    @SerializedName("weather")
    private ArrayList<WeatherState> weatherState;
    @SerializedName("coord")
    private Coordinates coordinates;
    @SerializedName("main")
    private WeatherDescription weatherDescription;
    private Wind wind;
    private String locationName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<WeatherState> getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(ArrayList<WeatherState> weatherState) {
        this.weatherState = weatherState;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public WeatherDescription getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(WeatherDescription weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
