package com.cjrodriguez.weatherforecast.repository;

import static com.cjrodriguez.weatherforecast.util.Constants.NO;

import android.util.Log;

import com.cjrodriguez.weatherforecast.datasource.cache.WeatherDao;
import com.cjrodriguez.weatherforecast.datasource.network.RetrofitService;
import com.cjrodriguez.weatherforecast.datastore.SettingsDatastore;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private final RetrofitService retrofitService;
    private final WeatherDao weatherDao;
    private final SettingsDatastore datastore;

    public WeatherRepositoryImpl(RetrofitService retrofitService, WeatherDao weatherDao,
                                 SettingsDatastore datastore) {
        this.retrofitService = retrofitService;
        this.weatherDao = weatherDao;
        this.datastore = datastore;
    }

    @Override
    public Single<List<Location>> getSearchLocations(String query) {
        return retrofitService.searchLocations(
                //BuildConfig.API_KEY,
                query);
    }

    @Override
    public Single<WeatherData> getWeatherForecastFromCache(String query, boolean isToday) {
        return Single.fromCallable(() -> {
            if(isToday){
                return weatherDao.getCurrentWeatherData(query);
            } else {
                return weatherDao.getTomorrowWeatherData(query);
            }
        });
    }

    @Override
    public Single<WeatherData> updateWeatherData(String query) {

        return retrofitService
                .getWeatherDataForecast(
                        //BuildConfig.API_KEY,
                        query,
                        NO, 3, NO).doOnSuccess((weatherData -> {
                          if(weatherData.getForecast().getForecastday().size()>0){
                              weatherDao.clearAllData();
                              weatherDao.insertAllData(weatherData.getLocation(),
                                      weatherData.getCurrent(), List.of(weatherData.getForecast()));
                          }
                } )).doOnError((er->{
                    Log.e("er", er.getMessage());
                }));
    }

    @Override
    public Single<List<Forecastday>> getAllForecastData() {
        return Single.fromCallable(weatherDao::getListForecast);
    }

    @Override
    public void writeSelectedCity(String country) {
        datastore.writeSelectedCity(country);
    }

    @Override
    public void writeLocationCity(String country) {
        datastore.writeLocationCity(country);
    }

    @Override
    public String readSelectedCity() {
        return datastore.readSelectedCity();
    }

    @Override
    public String readLocationCity() {
        return datastore.readLocationCity();
    }

}
