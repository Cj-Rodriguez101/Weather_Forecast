package com.cjrodriguez.weatherforecast.model;

public class Astro{
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String moon_phase;
    private String moon_illumination;
    private int is_moon_up;
    private int is_sun_up;

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public String getMoon_phase() {
        return moon_phase;
    }

    public String getMoon_illumination() {
        return moon_illumination;
    }

    public int getIs_moon_up() {
        return is_moon_up;
    }

    public int getIs_sun_up() {
        return is_sun_up;
    }
}
