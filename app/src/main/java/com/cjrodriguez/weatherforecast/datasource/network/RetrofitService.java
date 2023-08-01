package com.cjrodriguez.weatherforecast.datasource.network;

import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("search.json")
    Single<List<Location>> searchLocations(
            @Query("q") String searchString
    );

    @GET("forecast.json")
    Single<WeatherData> getWeatherDataForecast(
            @Query("q") String searchString,
            @Query("aqi") String aqi,
            @Query("days") int days,
            @Query("alerts") String alerts
    );
}