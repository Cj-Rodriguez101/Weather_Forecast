package com.cjrodriguez.weatherforecast.datasource.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cjrodriguez.weatherforecast.converter.Converters;
import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;

@TypeConverters(Converters.class)
@Database(
        entities = {Location.class, Current.class, Forecast.class},
        version = 1,
        exportSchema = false
)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();
}
