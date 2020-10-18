package com.weatherapp.repository;

import android.database.Cursor;

import com.google.gson.Gson;
import com.weatherapp.model.Weather;
import com.weatherapp.repository.database.DatabaseUtils;
import com.weatherapp.repository.database.Query;
import com.weatherapp.utils.ActivityManager;

import java.util.ArrayList;

public class WeatherLocalDataSource {
    private DatabaseUtils databaseUtils;
    private ActivityManager activityManager;
    private final static String DEFAULT_WHERE_WEATHER = " where id = ";

    public WeatherLocalDataSource(DatabaseUtils dbUtils, ActivityManager manager) {
        databaseUtils = dbUtils;
        activityManager = manager;
    }

    public Weather runGetWeatherQuery(long id) {
        String where;
        if (id == 0) {
            where = " ";
        } else {
            where = DEFAULT_WHERE_WEATHER + String.valueOf(id);
        }

        Cursor cursor = databaseUtils.rawQuery(Query.GET_WEATHER_QUERY, where);
        Weather weather = new Weather();
        while (cursor.moveToNext()) {
            String json = cursor.getString(cursor.getColumnIndex("weatherJson"));
            weather = new Gson().fromJson(json, Weather.class);
        }
        cursor.close();
        return weather;
    }

    public ArrayList<Weather> runGetAllWeatherQuery() {
        String where = "";
        Cursor cursor = databaseUtils.rawQuery(Query.GET_WEATHER_QUERY, where);
        ArrayList<Weather> weatherList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String json = cursor.getString(cursor.getColumnIndex("weatherJson"));
            Weather weather = new Gson().fromJson(json, Weather.class);
            weatherList.add(weather);
        }
        cursor.close();
        return weatherList;
    }

    public boolean runDeleteWeatherQuery(long id) {
        String where;
        if (id == 0) {
            where = "";
        } else {
            where = DEFAULT_WHERE_WEATHER + String.valueOf(id);
        }

        databaseUtils.execSQLQuery("queries/DeleteWeatherQuery.sql", where);
        return true;
    }

    public void runCreateWeatherQuery(String values) {
        databaseUtils.execSQLQuery("queries/CreateWeatherQuery.sql", values);
    }
}
