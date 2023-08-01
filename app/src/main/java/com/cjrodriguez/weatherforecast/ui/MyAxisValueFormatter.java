package com.cjrodriguez.weatherforecast.ui;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyAxisValueFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return String.format("%02d:%02d", (int)value, (int)((value - (int)value) * 60));
    }
}
