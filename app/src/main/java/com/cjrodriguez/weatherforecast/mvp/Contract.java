package com.cjrodriguez.weatherforecast.mvp;

import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.model.WeatherData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Contract {
    interface View{
        void showProgress();
        void hideProgress();
//        String getSearchLocation();

        default String getDefaultLocation(){return "";};
//        void setDefaultLocation(String query);
        default void setCurrentTemperature(String temp){};
        default void setUpdatedWeatherData(UpdatedWeatherData updatedWeatherData){};
        default void setCurrentLocation(String city){};
        default void setCurrentTempImage(){};

        default void updateFragments(){}

        default void setSearchLocationList(List<Location> locationList){};

        default void setLineTodayChartData(UpdatedWeatherData updatedWeatherData){};
    }

    interface Presenter {
        default void searchLocation(String query){};
        default void loadAllWeatherData(String location){};
        default void updateWeatherCache(String location){};

        default void writeSelectedCountry(String country){}
        default String readSelectedCountry(){return "";}
        default void unsubscribe(){};
    }

    interface Model {
        Single<List<Location>> searchLocation(String query);

        Single<UpdatedWeatherData> getCurrentWeatherData(String location, boolean isToday);
        default Single<UpdatedWeatherData> updateWeatherCache(String location){return null;}

        default void writeSelectedCountry(String country){}

        default String readSelectedCountry(){return  null;}

        default void clearAllWeatherData(){}

        default void insertUpdateWeatherData(Location location, Current current,
                                             List<Forecast> forecastList){}
    }
}
