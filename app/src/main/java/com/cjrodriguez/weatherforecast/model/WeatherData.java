package com.cjrodriguez.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("location")
    private Location location;

    @SerializedName("current")
    private Current Current;

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return Current;
    }
}
