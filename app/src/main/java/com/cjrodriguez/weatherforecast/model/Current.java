package com.cjrodriguez.weatherforecast.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currentTable")
public class Current {

    @PrimaryKey
    private int last_updated_epoch;
    private final String last_updated;
    private double temp_c;
    private final int is_day;

    private Condition condition;
    private final double wind_kph;
    private final int wind_degree;
    private final String wind_dir;
    private final double pressure_in;
    private final double precip_in;
    private final int humidity;
    private final int cloud;
    private double feelslike_c;
    private final double feelslike_f;
    private final double vis_km;
    private final double uv;
    private final double gust_kph;

    public Current(int last_updated_epoch, String last_updated, double temp_c, int is_day,
                   Condition condition, double wind_kph, int wind_degree,
                   String wind_dir, double pressure_in, double precip_in,
                   int humidity, int cloud, double feelslike_c, double feelslike_f,
                   double vis_km, double uv, double gust_kph) {
        this.last_updated_epoch = last_updated_epoch;
        this.last_updated = last_updated;
        this.temp_c = temp_c;
        this.is_day = is_day;
        this.condition = condition;
        this.wind_kph = wind_kph;
        this.wind_degree = wind_degree;
        this.wind_dir = wind_dir;
        this.pressure_in = pressure_in;
        this.precip_in = precip_in;
        this.humidity = humidity;
        this.cloud = cloud;
        this.feelslike_c = feelslike_c;
        this.feelslike_f = feelslike_f;
        this.vis_km = vis_km;
        this.uv = uv;
        this.gust_kph = gust_kph;
    }

    public void setLast_updated_epoch(int last_updated_epoch) {
        this.last_updated_epoch = last_updated_epoch;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setFeelslike_c(double feelslike_c) {
        this.feelslike_c = feelslike_c;
    }

    public int getLast_updated_epoch() {
        return last_updated_epoch;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public int getIs_day() {
        return is_day;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getWind_kph() {
        return wind_kph;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public double getPressure_in() {
        return pressure_in;
    }

    public double getPrecip_in() {
        return precip_in;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelslike_c() {
        return feelslike_c;
    }

    public double getFeelslike_f() {
        return feelslike_f;
    }

    public double getVis_km() {
        return vis_km;
    }

    public double getUv() {
        return uv;
    }

    public double getGust_kph() {
        return gust_kph;
    }
}