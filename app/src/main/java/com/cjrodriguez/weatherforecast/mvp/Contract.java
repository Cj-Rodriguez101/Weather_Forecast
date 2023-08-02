package com.cjrodriguez.weatherforecast.mvp;

import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Contract {
    interface View{
        default void showProgress(){}
        default void hideProgress(){}

        default String getDefaultLocation(){return "";}
        default void setUpdatedWeatherData(WeatherData weatherData){}
        default void setCurrentLocation(String city){}

        default void updateFragments(){}

        default void setSearchLocationList(List<Location> locationList){}

        default void setLineTodayChartData(WeatherData weatherData){}

        default void setFutureListData(List<Forecastday> forecastDayList){}

        default void showErrorLayout(String message){}
        default void hideErrorLayout(){}
    }

    interface Presenter {
        default void searchLocation(String query){}
        default void loadAllWeatherData(String location){}
        default void updateWeatherCache(String location){}

        default void writeSelectedCountry(String country){}
        default void writeLocationCountry(String country){}
        default String readSelectedCountry(){return "";}
        default String readLocationCountry(){return "";}
        default void unsubscribe(){}
    }

    interface Model {
        Single<List<Location>> searchLocation(String query);

        Single<WeatherData> getCurrentWeatherData(String location, boolean isToday);
        default Single<WeatherData> updateWeatherCache(String location){return null;}

        default Single<List<Forecastday>> getForecasts(){return null;}

        default void writeSelectedCountry(String country){}

        default String readSelectedCountry(){return  null;}

        default void writeLocationCountry(String country){}

        default String readLocationCountry(){return  null;}
    }
}
