package com.weatherapp.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BringGlobalTestDB.db";
    private static final String DATABASE_FOLDER = "/databases/";
    private Context currentContext;
    private static DatabaseManager instance = null;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        currentContext = context;
    }

    public static DatabaseManager getInstanceDB(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
            instance.databaseSetUp();
        }
        return instance;
    }

    private void databaseSetUp() {
        File file = new File(currentContext.getApplicationInfo().dataDir + DATABASE_FOLDER + DATABASE_NAME);
        if (!file.exists()) {
            createDatabaseFolder();
            copyDatabase();
        }
    }

    // Method to create folder if not exists
    public boolean createDatabaseFolder() {
        String dbPath = currentContext.getApplicationInfo().dataDir + DATABASE_FOLDER;
        File dbFile = currentContext.getDatabasePath(dbPath);
        dbFile.delete();
        return new File(dbPath).mkdirs();
    }

    private void copyDatabase() {
        try {
            File dbFile = currentContext.getDatabasePath(DATABASE_NAME);
            if (dbFile != null) {
                InputStream inputS = currentContext.getAssets().open(DATABASE_NAME);
                OutputStream os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                while (inputS.read(buffer) > 0) {
                    os.write(buffer);
                }
                os.flush();
                os.close();
                inputS.close();
            }
        } catch (IOException exception) {
            Log.d("COPY DATABASE ERROR", exception.getMessage());
        }
    }

    // This method will called when database is updated
    public void setUpDatabase() {
        shutDownDatabase();
        updateCurrentDatabase();
    }

    // Shut down all cursors active
    private void shutDownDatabase() {
        this.close();
    }

    private void updateCurrentDatabase() {
        instance = new DatabaseManager(currentContext);
    }

    // Get SQLiteDatabase readable mode
    public SQLiteDatabase getReadable() {
        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWriteable() {
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}


