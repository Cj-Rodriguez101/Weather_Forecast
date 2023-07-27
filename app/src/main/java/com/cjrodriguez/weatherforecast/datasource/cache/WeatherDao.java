package com.cjrodriguez.weatherforecast.datasource.cache;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.util.Util;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class WeatherDao {

    @Query("SELECT * FROM locationTable WHERE name =:query")
    abstract Location getLocationData(String query);

    @Query("SELECT * FROM currentTable")
    abstract Current getCurrentSingleData();

    @Query("SELECT * FROM forecastTable")
    abstract List<Forecast> getForecastData();

    @Query("DELETE FROM locationTable")
    abstract void deleteLocationData();

    @Query("DELETE FROM currentTable")
    abstract void deleteCurrentWeatherData();

    @Query("DELETE FROM forecastTable")
    abstract void deleteForecastData();

    @Transaction
    public void clearAllData(){
        deleteCurrentWeatherData();
        deleteLocationData();
        deleteForecastData();
    }

    @Transaction
    public UpdatedWeatherData getCurrentWeatherData(String query){
        Location location = getLocationData(query);
        Current current = this.getCurrentSingleData();
        List<Forecast> forecasts = getForecastData();

        return new UpdatedWeatherData(location, current, forecasts.get(0));
    }

    @Transaction
    public UpdatedWeatherData getTomorrowWeatherData(String query){
        Location location = getLocationData(query);
        List<Forecast> forecasts = getForecastData();
        List<Forecastday> forecastDayList = forecasts.get(0).getForecastday();
//        if(!forecastDayList.isEmpty()){
//
//        }

        return new Util().getCurrentWeatherFromForecast(forecastDayList.get(1), location);

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertLocation(Location locationList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertCurrent(Current Current);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertForecast(List<Forecast> forecastList);

    @Transaction
    public void insertAllData(Location location, Current current, List<Forecast> forecastList){
        insertLocation(location);
        insertCurrent(current);
        insertForecast(forecastList);
    }
}
