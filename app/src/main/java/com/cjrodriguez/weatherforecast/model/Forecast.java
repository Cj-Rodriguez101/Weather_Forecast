package com.cjrodriguez.weatherforecast.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "forecastTable")
public class Forecast{

    @PrimaryKey
    private int id;
    private List<Forecastday> forecastday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return id == forecast.id && forecastday.equals(forecast.forecastday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forecastday);
    }

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