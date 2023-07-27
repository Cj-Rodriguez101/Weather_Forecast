package com.cjrodriguez.weatherforecast.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "forecastTable")
public class Forecast{

    @PrimaryKey
    private int id;
    private List<Forecastday> forecastday;

    public Forecast(int id, List<Forecastday> forecastday) {
        this.id = id;
        this.forecastday = forecastday;
    }

    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}