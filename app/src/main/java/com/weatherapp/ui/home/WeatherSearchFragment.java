package com.weatherapp.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.weatherapp.BaseApp;
import com.weatherapp.R;
import com.weatherapp.databinding.FragmentWeatherSearchBinding;
import com.weatherapp.model.Weather;
import com.weatherapp.ui.MapsFragment;
import com.weatherapp.ui.home.adapters.HomeSearchWeatherAdapter;
import com.weatherapp.utils.ApplicationManager;
import com.weatherapp.utils.SaveBookmark;
import com.weatherapp.viewModel.WeatherSearchViewModel;

public class WeatherSearchFragment extends Fragment implements SaveBookmark {
    private FragmentWeatherSearchBinding fragmentWeatherSearchBinding;
    private WeatherSearchViewModel weatherSearchViewModel;
    private ApplicationManager applicationManager;
    private HomeSearchWeatherAdapter homeSearchWeatherAdapter;

    public WeatherSearchFragment(WeatherSearchViewModel viewModel, ApplicationManager appManager) {
        weatherSearchViewModel = viewModel;
        applicationManager = appManager;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentWeatherSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_search, container, false);
        setListeners();
        addSubscriptions();
        buildAdapter();
        weatherSearchViewModel.getAllBookmarksDB();
        return fragmentWeatherSearchBinding.getRoot();
    }

    private void buildAdapter() {
        homeSearchWeatherAdapter = new HomeSearchWeatherAdapter();
        fragmentWeatherSearchBinding.rvBookmark.setAdapter(homeSearchWeatherAdapter);
        fragmentWeatherSearchBinding.rvBookmark.setLayoutManager(new LinearLayoutManager(BaseApp.getApplicationManager().getCurrentContext()));
    }

    private void addSubscriptions() {
        weatherSearchViewModel.deleteWeatherInformationLiveData().observe(getViewLifecycleOwner(), isDeleting -> {
            if (isDeleting) {
                showDialog();
            }
        });

        weatherSearchViewModel.getWeatherListLiveData().observe(getViewLifecycleOwner(), weatherList -> {
            homeSearchWeatherAdapter.setBookmarkInformation(weatherList, weatherSearchViewModel.deleteWeatherInformation);
        });
    }

    private void showDialog() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(BaseApp.getApplicationManager().getCurrentContext());
        dialogo1.setTitle("Delete bookmark");
        dialogo1.setMessage("Want you delete the bookmark?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    private void setListeners() {
        fragmentWeatherSearchBinding.mapOptionContainer.setOnClickListener(view -> {
            BaseApp.getApplicationManager().mapsFragment.setOnSaveBookMark(this);
            BaseApp.getApplicationManager().getActivityManager().goToFragment(BaseApp.getApplicationManager().mapsFragment, R.id.fragment_container, null, false, true);
        });
    }

    @Override
    public void onTapMapBookmark(Weather weather) {
        weatherSearchViewModel.saveBookmark(weather);
    }
}