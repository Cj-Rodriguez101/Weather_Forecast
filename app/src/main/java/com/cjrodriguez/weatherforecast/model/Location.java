package com.cjrodriguez.weatherforecast.model;

import static com.cjrodriguez.weatherforecast.util.Constants.CURRENT_LOCATION_STRING;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "locationTable")
public class Location {
    @PrimaryKey
    private int id;
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    private int localtime_epoch;
    private String url;

    public Location (String country){
        this.id = 1;
        this.name = country+" "+CURRENT_LOCATION_STRING;
        this.region = country;
        this.country = country;
        this.lat = 98.875;
        this.lon = 98.34555;
        this.localtime_epoch = 0;
        this.url = "random";
    }

    public int getLocaltime_epoch() {
        return localtime_epoch;
    }

    public void setLocaltime_epoch(int localtime_epoch) {
        this.localtime_epoch = localtime_epoch;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", localtime_epoch=" + localtime_epoch +
                ", url='" + url + '\'' +
                '}';
    }
}