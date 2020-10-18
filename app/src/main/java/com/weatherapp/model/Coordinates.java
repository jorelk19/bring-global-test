package com.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
}
