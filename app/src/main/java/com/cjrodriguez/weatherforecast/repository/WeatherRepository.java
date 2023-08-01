package com.cjrodriguez.weatherforecast.repository;

import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface WeatherRepository {

    Single<List<Location>> getSearchLocations(String query);
    Single<WeatherData> getWeatherForecastFromCache(String query, boolean isToday);

    Single<WeatherData> updateWeatherData(String query);

    Single<List<Forecastday>> getAllForecastData();

    void writeSelectedCity(String country);

    void writeLocationCity(String country);
    String readSelectedCity();
    String readLocationCity();
}
