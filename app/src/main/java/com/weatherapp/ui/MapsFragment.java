package com.weatherapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.weatherapp.R;
import com.weatherapp.databinding.FragmentMapsBinding;
import com.weatherapp.model.Weather;
import com.weatherapp.utils.MapManager;
import com.weatherapp.utils.OnMapTouched;
import com.weatherapp.utils.SaveBookmark;
import com.weatherapp.viewModel.MapViewModel;

public class MapsFragment extends MapManager implements OnMapTouched {
    public static final String BUNDLE_INTERFACE = "BUNDLE_INTERFACE";
    private FragmentMapsBinding fragmentMapsBinding;
    private static MapViewModel mapViewModel;
    private Weather currentWeather;
    private SaveBookmark onSaveBookmark;

    public MapsFragment(MapViewModel viewModel) {
        mapViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMapsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setOnMapTouched(this);
        addSubscriptions();

        return fragmentMapsBinding.getRoot();
    }


    private void addSubscriptions() {
        mapViewModel.getWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                currentWeather = weather;
                fragmentMapsBinding.tvTemperature.setText(weather.getWeatherDescription().getTemperature());
                String humidity = String.valueOf(weather.getWeatherDescription().getHumidity());
                fragmentMapsBinding.tvHumidity.setText(humidity);
                fragmentMapsBinding.tvRain.setText(weather.getWeatherState().get(0).getState());
                String speed = String.valueOf(weather.getWind().getSpeed());
                fragmentMapsBinding.tvWindy.setText(speed);
            }
        });

        mapViewModel.showWeatherLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showWeatherInformation) {
                if (showWeatherInformation) {
                    fragmentMapsBinding.weatherContainer.setVisibility(View.VISIBLE);
                } else {
                    fragmentMapsBinding.weatherContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void getWeatherOnTouchMap(double lat, double lng) {
        mapViewModel.getWeatherByLocation(lat, lng);
    }

    @Override
    public void showWeatherInformation(boolean isShowing) {
        if (isShowing) {
            onSaveBookmark.onTapMapBookmark(currentWeather);
            fragmentMapsBinding.weatherContainer.setVisibility(View.VISIBLE);
        } else {
            fragmentMapsBinding.weatherContainer.setVisibility(View.GONE);
        }
    }

    public void setOnSaveBookMark(SaveBookmark saveBookMark) {
        onSaveBookmark = saveBookMark;
    }
}