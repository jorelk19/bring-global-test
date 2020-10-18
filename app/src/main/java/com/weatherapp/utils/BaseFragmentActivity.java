package com.weatherapp.utils;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.weatherapp.BaseApp;
import com.weatherapp.ui.MainActivity;

public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApp.getApplicationManager().getActivityManager().setCurrentActivity(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        BaseApp.getApplicationManager().getActivityManager().setCurrentActivity(this);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if(BaseApp.getApplicationManager().getActivityManager().getCurrentActivity().getClass() != MainActivity.class) {
                super.onBackPressed();
            }
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}
