package com.weatherapp.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.weatherapp.BaseApp;
import com.weatherapp.R;
import com.weatherapp.databinding.ActivityMainBinding;
import com.weatherapp.utils.BaseFragmentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseFragmentActivity{

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setBottomNavigationListener();
        setSelectedBottomNavigationItem();
        setListeners();
    }

    private void setListeners() {
        activityMainBinding.headerContainer.ivBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setSelectedBottomNavigationItem() {
        activityMainBinding.mainBottomNavigation.getMenu().getItem(0).setChecked(true);
        BaseApp.getApplicationManager().getActivityManager().goToFragment(
                BaseApp.getApplicationManager().weatherSearchFragment,
                R.id.fragment_container,
                null,
                false,
                true);
        activityMainBinding.headerContainer.tvHeaderDescription.setText("Weather Search");
    }

    private void setBottomNavigationListener() {
        activityMainBinding.mainBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_weather_search:
                        if (!item.isChecked()) {
                            BaseApp.getApplicationManager().getActivityManager().goToFragment(
                                    BaseApp.getApplicationManager().weatherSearchFragment,
                                    R.id.fragment_container,
                                    null,
                                    false,
                                    false);
                            activityMainBinding.headerContainer.tvHeaderDescription.setText("Weather Search");
                            return true;
                        } else {
                            return false;
                        }
                    case R.id.action_weather_list:
                        if (!item.isChecked()) {
                            return true;
                        } else {
                            return false;
                        }
                }
                return false;
            }
        });
    }
}