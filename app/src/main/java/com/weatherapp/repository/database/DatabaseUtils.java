package com.weatherapp.repository.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.weatherapp.utils.ApplicationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseUtils {

    private ApplicationManager applicationManager;

    public DatabaseUtils(ApplicationManager manager) {
        applicationManager = manager;
    }

    //Run specific query
    public Cursor rawQuery(Query query, String whereBody) {
        switch (query) {
            case GET_WEATHER_QUERY:
                return readSqlFileAndReplace("queries/GetWeatherQuery.sql", whereBody);
            case DELETE_WEATHER_QUERY:
                return readSqlFileAndReplace("queries/DeleteWeatherQuery.sql", whereBody);
            case CREATE_WEATHER_QUERY:
                return readSqlFileAndReplace("queries/CreateWeatherQuery.sql", whereBody);
            default:
                return null;
        }
    }

    public void execSQLQuery(String nameFile, String whereBody) {
        try {
            InputStream inputStream = applicationManager.getCurrentContext().getResources().getAssets().open(nameFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;
            line = reader.readLine();
            while (line != null) {
                content.append(line);
                line = reader.readLine();
            }
            DatabaseManager.getInstanceDB(applicationManager.getCurrentContext()).setUpDatabase();
            SQLiteDatabase db = DatabaseManager.getInstanceDB(applicationManager.getCurrentContext()).getReadableDatabase();
            String finalQuery = "";
            if (whereBody != "") {
                finalQuery = content.toString().replace(":queryValues", " " + whereBody);
            } else {
                finalQuery = content.toString().replace(":queryValues", " ");
            }
            db.execSQL(finalQuery);
        }catch (IOException exception){
            Log.d("CREATE WEATHER ERROR DB", exception.getMessage());
        }
    }

    // Read Sql file and replace it with 'where' expression
    private Cursor readSqlFileAndReplace(String nameFile, String whereBody) {
        SQLiteDatabase db = DatabaseManager.getInstanceDB(applicationManager.getCurrentContext()).getReadableDatabase();
        String finalQuery = "";
        try {
            InputStream inputStream = applicationManager.getCurrentContext().getResources().getAssets().open(nameFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;
            line = reader.readLine();
            while (line != null) {
                content.append(line);
                line = reader.readLine();
            }
            DatabaseManager.getInstanceDB(applicationManager.getCurrentContext()).setUpDatabase();

            if (whereBody != "") {
                finalQuery = content.toString().replace(":queryValues", " " + whereBody);
            } else {
                finalQuery = content.toString().replace(":queryValues", " ");
            }
        } catch (IOException exception) {
            Log.d("READ SQL FILE ERROR", exception.getMessage());
        }
        return db.rawQuery(finalQuery, null);
    }
}

