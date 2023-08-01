package com.cjrodriguez.weatherforecast.model;

public class Day{
    private final double maxtemp_c;
    private final double mintemp_c;
    private double avgtemp_c;
    private double maxwind_kph;
    private double avghumidity;
    private int daily_will_it_rain;
    private int daily_chance_of_rain;
    private Condition condition;
    private double uv;

    public Day() {
        this.maxtemp_c = 0;
        this.mintemp_c = 0;
    }

    public double getMaxtemp_c() {
            return maxtemp_c;

    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public double getMaxwind_kph() {
        return maxwind_kph;
    }

    public void setMaxwind_kph(double maxwind_kph) {
        this.maxwind_kph = maxwind_kph;
    }

    public double getAvghumidity() {
        return avghumidity;
    }

    public void setAvghumidity(double avghumidity) {
        this.avghumidity = avghumidity;
    }

    public int getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    public void setDaily_chance_of_rain(int daily_chance_of_rain) {
        this.daily_chance_of_rain = daily_chance_of_rain;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getUv() {
        return uv;
    }
}
