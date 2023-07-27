package com.cjrodriguez.weatherforecast.model;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.util.List;

public class Forecastday{
    private String date;
    private int date_epoch;
    private Day day;
    //private Astro astro;
    private List<Hour> hour;

    public String getDate() {
        return date;
    }

    public int getDate_epoch() {
        return date_epoch;
    }

    public Day getDay() {
        return day;
    }

//    public Astro getAstro() {
//        return astro;
//    }

    public List<Hour> getHour() {
        return hour;
    }
}
