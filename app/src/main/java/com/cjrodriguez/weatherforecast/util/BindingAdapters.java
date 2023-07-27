package com.cjrodriguez.weatherforecast.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.model.Day;

public class BindingAdapters {

    public static BindingAdapters instance;

    private BindingAdapters() {}
    public static synchronized BindingAdapters getInstance()
    {
        if (instance == null){
            instance = new BindingAdapters();
        }

        return instance;
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl !=null){

            Glide.with(view.getContext()).load("https:"+imageUrl).centerCrop()
                    .placeholder(R.drawable.search)
                    .into(view);
        }
        else{
            view.setImageResource(R.drawable.search);
        }
    }

    @BindingAdapter("double")
    public static void parseDouble(TextView view, double temp_c) {
        view.setText(String.valueOf(Math.round(temp_c)));
    }

    @BindingAdapter("chance")
    public static void parseDouble(TextView view, int chanceOfRain) {

        String text = chanceOfRain+"% chance of precipitation";
        if (chanceOfRain == 0){
            text = "No chance of rain";
        }
        view.setText(text);
    }

    @BindingAdapter("forecastDay")
    public static void parseDay(TextView view, Day day) {

        long maxTemp = 0;
        long minTemp = 0;
        if(day!=null){
            maxTemp = Math.round(day.getMaxtemp_c());
            minTemp = Math.round(day.getMintemp_c());
        }

        String text = "Day "+Math.round(maxTemp)+" - Night "+Math.round(minTemp);
        view.setText(text);
    }
}
