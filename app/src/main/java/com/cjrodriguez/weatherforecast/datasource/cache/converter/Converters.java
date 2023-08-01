package com.cjrodriguez.weatherforecast.datasource.cache.converter;

import androidx.room.TypeConverter;

import com.cjrodriguez.weatherforecast.model.Condition;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static Condition fromString(String value) {
        return new Gson().fromJson(value, Condition.class);
    }

    @TypeConverter
    public static String toString(Condition condition) {
        return new Gson().toJson(condition);
    }

    @TypeConverter
    public static List<Forecastday> fromStringToForecastList(String value) {
        Type listType = new TypeToken<List<Forecastday>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toForecastdayList(List<Forecastday> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
