package com.cjrodriguez.weatherforecast.model;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.util.List;
import java.util.Objects;

public class Forecastday{
    private String date;
    private int date_epoch;
    private Day day;
    private Astro astro;
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

    public Astro getAstro() {
        return astro;
    }

    public List<Hour> getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecastday that = (Forecastday) o;
        return date_epoch == that.date_epoch && Objects.equals(date, that.date)
                && Objects.equals(day, that.day) && Objects.equals(astro, that.astro)
                && Objects.equals(hour, that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, date_epoch, day, astro, hour);
    }
}
