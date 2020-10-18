package com.weatherapp.ui.home.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.R;
import com.weatherapp.databinding.LayoutWeatherListItemBinding;
import com.weatherapp.model.Weather;

import java.util.ArrayList;

public class HomeSearchWeatherAdapter extends RecyclerView.Adapter<HomeSearchWeatherViewHolder> {
    private LayoutWeatherListItemBinding layoutWeatherListItemBinding;
    ArrayList<Weather> weatherList = new ArrayList<>();
    private MutableLiveData<Boolean> deleteBookmark;

    @NonNull
    @Override
    public HomeSearchWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutWeatherListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_weather_list_item, parent, false);
        return new HomeSearchWeatherViewHolder(layoutWeatherListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSearchWeatherViewHolder holder, int position) {
        holder.bind(weatherList.get(position), deleteBookmark);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setBookmarkInformation(ArrayList<Weather> list, MutableLiveData<Boolean> delBookmark) {
        deleteBookmark = delBookmark;
        weatherList.clear();
        weatherList.addAll(list);
        notifyDataSetChanged();
    }
}
