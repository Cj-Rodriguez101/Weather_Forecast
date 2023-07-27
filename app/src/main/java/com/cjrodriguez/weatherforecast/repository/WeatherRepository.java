package com.cjrodriguez.weatherforecast.repository;

import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface WeatherRepository {

    Single<List<Location>> getSearchLocations(String query);
    Single<WeatherData> getCurrentWeatherData(String query);
    Single<UpdatedWeatherData> getWeatherForecastFromCache(String query, boolean isToday);

    Single<UpdatedWeatherData> updateWeatherData(String query);

    void writeWeatherCountryName(String country);
    String readWeatherCountryName();

    void clearAllData();

    void insertAllData(Location location, Current current, List<Forecast> forecastList);

//    Single<Current> getCurrentData();
}
