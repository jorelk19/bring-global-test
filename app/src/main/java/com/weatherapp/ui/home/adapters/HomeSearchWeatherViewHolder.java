package com.weatherapp.ui.home.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.databinding.LayoutWeatherListItemBinding;
import com.weatherapp.model.Weather;

public class HomeSearchWeatherViewHolder extends RecyclerView.ViewHolder {
    private LayoutWeatherListItemBinding layoutWeatherListItemBinding;
    private MutableLiveData<Boolean> deleteBookmark;
    public HomeSearchWeatherViewHolder(@NonNull LayoutWeatherListItemBinding itemView) {
        super(itemView.getRoot());
        layoutWeatherListItemBinding = itemView;
        setListener();
    }

    public void bind(Weather weather, MutableLiveData<Boolean> delBookmark){
        layoutWeatherListItemBinding.tvLocationName.setText(weather.getLocationName());
        deleteBookmark = delBookmark;
    }

    private void setListener() {
        layoutWeatherListItemBinding.btnDeleteBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBookmark.setValue(true);
            }
        });
    }
}
