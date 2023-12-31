package com.cjrodriguez.weatherforecast.model;

import androidx.room.Embedded;

public class WeatherData {
    @Embedded
    private final Location location;
    @Embedded
    private final Current current;
    @Embedded
    private final Forecast forecast;

    public WeatherData(Location location, Current current, Forecast forecast) {
        this.location = location;
        this.current = current;
        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }


}
