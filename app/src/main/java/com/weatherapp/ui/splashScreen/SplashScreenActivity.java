package com.weatherapp.ui.splashScreen;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.weatherapp.BaseApp;
import com.weatherapp.R;
import com.weatherapp.databinding.ActivitySplashScreenBinding;
import com.weatherapp.ui.MainActivity;
import com.weatherapp.utils.BaseFragmentActivity;

public class SplashScreenActivity extends BaseFragmentActivity {

    private ActivitySplashScreenBinding activitySplashScreenBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        Handler handler = new Handler();
        Runnable runnable = () -> {
            BaseApp.getApplicationManager().getActivityManager().goTo(MainActivity.class, null, null);
            finish();
        };
        handler.postDelayed(runnable, 2000);
    }
}
