package com.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class WeatherDescription {
    @SerializedName("temp")
    private String temperature;
    @SerializedName("feels_like")
    private float thermalSensation;
    @SerializedName("temp_min")
    private float minimumTemperature;
    @SerializedName("temp_max")
    private float maximumTemperature;
    private Integer pressure;
    private Integer humidity;
    @SerializedName("sea_level")
    private Integer seaLevel;
    @SerializedName("grnd_level")
    private Integer grandLevel;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public float getThermalSensation() {
        return thermalSensation;
    }

    public void setThermalSensation(float thermalSensation) {
        this.thermalSensation = thermalSensation;
    }

    public float getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(float minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public float getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(float maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Integer seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Integer getGrandLevel() {
        return grandLevel;
    }

    public void setGrandLevel(Integer grandLevel) {
        this.grandLevel = grandLevel;
    }
}
