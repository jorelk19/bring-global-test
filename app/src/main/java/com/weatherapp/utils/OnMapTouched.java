package com.weatherapp.utils;


public interface OnMapTouched {
    void getWeatherOnTouchMap(double lat, double lng);
    void showWeatherInformation(boolean isShowing, String locationName);
}
