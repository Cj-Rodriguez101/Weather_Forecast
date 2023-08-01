package com.cjrodriguez.weatherforecast.model;

public class Hour{
    private String time;
    private double temp_c;
    private Condition condition;

    public String getTime() {
        return time;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public Condition getCondition() {
        return condition;
    }
}