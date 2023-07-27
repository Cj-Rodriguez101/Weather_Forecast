package com.cjrodriguez.weatherforecast.mvp;

import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class WeatherModel implements Contract.Model {

    private final WeatherRepository repository;

    public WeatherModel(WeatherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<Location>> searchLocation(String query) {
        return repository.getSearchLocations(query);
    }

    @Override
    public Single<UpdatedWeatherData> getCurrentWeatherData(String query, boolean isToday) {
        return repository.getWeatherForecastFromCache(query, isToday);
    }

    @Override
    public Single<UpdatedWeatherData> updateWeatherCache(String location) {
        return repository.updateWeatherData(location);
    }

    @Override
    public void clearAllWeatherData() {
        repository.clearAllData();
        //Contract.Model.super.clearAllWeatherData();
    }

    @Override
    public void insertUpdateWeatherData(Location location, Current current, List<Forecast> forecastList) {
        repository.insertAllData(location, current, forecastList);
    }

    @Override
    public void writeSelectedCountry(String country) {
        repository.writeWeatherCountryName(country);
    }

    @Override
    public String readSelectedCountry() {
        return repository.readWeatherCountryName();
    }
}
