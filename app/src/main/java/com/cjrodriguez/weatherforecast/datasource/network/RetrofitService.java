package com.cjrodriguez.weatherforecast.datasource.network;

import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("search.json")
    Single<List<Location>> searchLocations(
            @Query("key") String apiKey,
            @Query("q") String searchString
    );

    @GET("current.json")
    Single<WeatherData> getCurrentWeatherData(
            @Query("key") String apiKey,
            @Query("q") String searchString,
            @Query("aqi") String aqi
    );

    @GET("forecast.json")
    Single<UpdatedWeatherData> getWeatherDataForecast(
            @Query("key") String apiKey,
            @Query("q") String searchString,
            @Query("aqi") String aqi,
            @Query("days") int days,
            @Query("alerts") String alerts
    );
};