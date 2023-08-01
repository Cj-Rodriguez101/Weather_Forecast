package com.cjrodriguez.weatherforecast.ui;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.model.Day;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Hour;
import com.cjrodriguez.weatherforecast.util.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BindingAdapters {

    public static BindingAdapters instance;

    private BindingAdapters() {}
    public static synchronized BindingAdapters getInstance()
    {
        if (instance == null){
            instance = new BindingAdapters();
        }

        return instance;
    }

    @BindingAdapter("progress")
    public static void setProgress(ProgressBar progress, boolean shouldShowProgress){
        progress.setVisibility(shouldShowProgress ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl !=null){

            Glide.with(view.getContext()).load("https:"+imageUrl).centerCrop()
                    .placeholder(R.drawable.search)
                    .into(view);
        }
        else{
            view.setImageResource(R.drawable.search);
        }
    }

    @BindingAdapter("double")
    public static void parseDouble(TextView view, double temp_c) {
        view.setText(String.valueOf(Math.round(temp_c)));
    }

    @BindingAdapter("temp")
    public static void parseTemp(TextView view, double temp_c) {
        String noWithoutDegree = String.valueOf(Math.round(temp_c))+" o";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(noWithoutDegree);
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableStringBuilder.setSpan(superscriptSpan, noWithoutDegree.length()-1,
                noWithoutDegree.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spannableStringBuilder);
    }

    @BindingAdapter("lineChart")
    public static void parseLineChart(LineChart lineChart, Forecastday forecastday){

        setLineChartData(lineChart, forecastday);

    }

    public static void setLineChartData(LineChart lineChart, Forecastday forecastday) {
        lineChart.setTouchEnabled(false);

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values1 = new ArrayList<>();

        int colorToSet = WHITE;

        Context context = lineChart.getContext();

        int nightModeFlags =
                context.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {

            case Configuration.UI_MODE_NIGHT_NO:
                colorToSet = BLACK;
                break;
        }

        List<Hour> hourList = forecastday.getHour();
        if (!hourList.isEmpty()) {
            for (int i = 0; i < hourList.size(); i++) {
                float hour = Float.parseFloat(hourList.get(i).getTime().substring(11, 13).replaceFirst("^0+(?!$)", ""));
                values.add(new Entry(hour, (float) hourList.get(i).getTemp_c()));
            }

            Util util = new Util();
            for (int i = 0; i < hourList.size(); i++) {
                float hour = Float.parseFloat(hourList.get(i).getTime().substring(11, 13).replaceFirst("^0+(?!$)", ""));
                String dayOrNight = hourList.get(i).getCondition().getIcon().split("/")[5];
                String no = hourList.get(i).getCondition().getIcon().split("/")[6].replace(".png", "");

                values1.add(new Entry(hour, -36f, ResourcesCompat
                        .getDrawable(context.getResources(),
                                util.getSpecificDrawable(dayOrNight, no), context.getTheme())));
            }

            LineDataSet set2 = new LineDataSet(values1, "DataSet 2");
            set2.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "";
                }
            });

            LineDataSet set1 = new LineDataSet(values, "DataSet 1");
            set1.setValueFormatter(new DefaultValueFormatter(0));
            set1.setDrawFilled(false);
            set1.setValueTextColor(colorToSet);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            set2.enableDashedLine(0f, 1f, 0f);
            dataSets.add(set1);

            dataSets.add(set2);

            LineData data = new LineData(dataSets);

            lineChart.getAxisLeft().setAxisLineColor(TRANSPARENT);
            lineChart.getAxisRight().setAxisLineColor(TRANSPARENT);

            lineChart.getAxisRight().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.getXAxis().setDrawGridLines(false);

            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            lineChart.getXAxis().setLabelCount(24);
            lineChart.getXAxis().setTextColor(colorToSet);
            lineChart.getLegend().setEnabled(false);
            lineChart.getAxisLeft().setAxisMinimum(-80);
            lineChart.getAxisLeft().setDrawLabels(false);
            lineChart.getAxisRight().setDrawLabels(false);
            lineChart.getDescription().setEnabled(false);

            lineChart.getXAxis().setValueFormatter(new MyAxisValueFormatter());

            lineChart.setData(data);

            lineChart.setMinimumWidth(3000);
            lineChart.animateX(1500);
        }
    }

    @BindingAdapter({"sunset", "sunrise"})
    public static void parseSunriseSunset(TextView view, String sunset, String sunrise){
        view.setText(sunrise +", "+ sunset);
    }

    @BindingAdapter("chance")
    public static void parseDouble(TextView view, int chanceOfRain) {

        String text = chanceOfRain+"% chance of precipitation";
        if (chanceOfRain == 0){
            text = "No chance of rain";
        }
        view.setText(text);
    }

    @BindingAdapter("forecastDay")
    public static void parseDay(TextView view, Day day) {

        long maxTemp = 0;
        long minTemp = 0;
        if(day!=null){
            maxTemp = Math.round(day.getMaxtemp_c());
            minTemp = Math.round(day.getMintemp_c());
        }

        String text = "Day "+Math.round(maxTemp)+" - Night "+Math.round(minTemp);
        view.setText(text);
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter({"currentDateTime", "isLocationSelected"})
    public static void parseCurrentDateTime(TextView view, int dateTime, boolean isLocationSelected){
        Date date = new java.util.Date(dateTime * 1000L);
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        DateFormat timeFormat = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        String time = timeFormat.format(dateTime);
//
        if(isLocationSelected){
            time = timeFormat.format(currentTime);
        }
        //view.setText(dateFormat.format(date)+" "+time);
        view.setText(dateFormat.format(date));
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("tomorrowDateTime")
    public static void parseTomorrowDateTime(TextView view, int dateTime){
        Date date = new java.util.Date(dateTime * 1000L);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        view.setText(dateFormat.format(date));
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("kmh")
    public static void parseKmH(TextView view, double text){
        view.setText(Math.round(text) +" km/h");
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("percentage")
    public static void parsePercentage(TextView view, double text){
        view.setText(Math.round(text)+"%");
    }

}
