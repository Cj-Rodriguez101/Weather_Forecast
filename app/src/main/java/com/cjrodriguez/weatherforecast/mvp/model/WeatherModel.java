package com.cjrodriguez.weatherforecast.mvp.model;

import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;
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
    public Single<WeatherData> getCurrentWeatherData(String query, boolean isToday) {
        return repository.getWeatherForecastFromCache(query, isToday);
    }

    @Override
    public Single<WeatherData> updateWeatherCache(String location) {
        return repository.updateWeatherData(location);
    }

    @Override
    public Single<List<Forecastday>> getForecasts() {
        return repository.getAllForecastData();
    }

    @Override
    public void writeSelectedCountry(String country) {
        repository.writeSelectedCity(country);
    }

    @Override
    public String readSelectedCountry() {
        return repository.readSelectedCity();
    }

    @Override
    public void writeLocationCountry(String country) {
       repository.writeLocationCity(country);
    }

    @Override
    public String readLocationCountry() {
        return repository.readLocationCity();
    }
}
