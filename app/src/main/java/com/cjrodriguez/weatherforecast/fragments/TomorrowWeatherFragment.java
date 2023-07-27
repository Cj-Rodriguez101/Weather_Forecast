package com.cjrodriguez.weatherforecast.fragments;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;
import static com.cjrodriguez.weatherforecast.util.Constants.SELECTED_CITY;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.databinding.FragmentTodayWeatherBinding;
import com.cjrodriguez.weatherforecast.databinding.FragmentTommorrowWeatherBinding;
import com.cjrodriguez.weatherforecast.model.Day;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Hour;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.WeatherModel;
import com.cjrodriguez.weatherforecast.mvp.presenters.TomorrowWeatherFragmentPresenter;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.util.BaseFragment;
import com.cjrodriguez.weatherforecast.util.MyAxisValueFormatter;
import com.cjrodriguez.weatherforecast.util.Util;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TomorrowWeatherFragment extends BaseFragment implements Contract.View {

    FragmentTommorrowWeatherBinding binding;

    Contract.Presenter presenter;

    @Inject
    WeatherRepository weatherRepository;
    public TomorrowWeatherFragment() {
        // Required empty public constructor
    }

    public static TomorrowWeatherFragment newInstance(String param1) {
        TomorrowWeatherFragment fragment = new TomorrowWeatherFragment();
        if(param1 != null){
            Bundle args = new Bundle();
            args.putString(SELECTED_CITY, param1);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((BaseApplication) getActivity().getApplicationContext()).appComponent.inject(this);
    }

    @Override
    public void setUpdatedWeatherData(UpdatedWeatherData updatedWeatherData) {
        binding.setTomorrowWeather(updatedWeatherData);
        List<Forecastday> forecastDayList = updatedWeatherData.getForecast().getForecastday();
        if (!forecastDayList.isEmpty()) {
            Day day = forecastDayList.get(0).getDay();
            binding.setTomorrowDay(day);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TomorrowWeatherFragmentPresenter(this,
                new WeatherModel(weatherRepository));
        String location = weatherRepository.readWeatherCountryName();
        if (!location.isEmpty()) {
            presenter.loadAllWeatherData(location);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTommorrowWeatherBinding.inflate(inflater, container, false);
        binding.lineChart.setTouchEnabled(false);
        return binding.getRoot();
    }

    @Override
    public void updateScreen() {
        presenter.loadAllWeatherData(presenter.readSelectedCountry());
    }

    @Override
    public void setLineTodayChartData(UpdatedWeatherData updatedWeatherData) {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values1 = new ArrayList<>();

        List<Forecastday> foreCastDayList = updatedWeatherData.getForecast().getForecastday();

        if (!foreCastDayList.isEmpty()) {

            int colorToSet = WHITE;

            int nightModeFlags =
                    getContext().getResources().getConfiguration().uiMode &
                            Configuration.UI_MODE_NIGHT_MASK;
            switch (nightModeFlags) {

                case Configuration.UI_MODE_NIGHT_NO:
                    colorToSet = BLACK;
                    break;
            }

            Forecastday forecastday = foreCastDayList.get(0);
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
                            .getDrawable(getContext().getResources(),
                                    util.getSpecificDrawable(dayOrNight, no), getContext().getTheme())));
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
                dataSets.add(set1); // add the data sets

                dataSets.add(set2);

                LineData data = new LineData(dataSets);

                binding.lineChart.getAxisLeft().setAxisLineColor(TRANSPARENT);
                binding.lineChart.getAxisRight().setAxisLineColor(TRANSPARENT);

                binding.lineChart.getAxisRight().setDrawGridLines(false);
                binding.lineChart.getAxisLeft().setDrawGridLines(false);
                binding.lineChart.getXAxis().setDrawGridLines(false);

                binding.lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                binding.lineChart.getXAxis().setLabelCount(24);
                binding.lineChart.getXAxis().setTextColor(colorToSet);
                binding.lineChart.getLegend().setEnabled(false);
                binding.lineChart.getAxisLeft().setDrawLabels(false);
                binding.lineChart.getAxisRight().setDrawLabels(false);
                binding.lineChart.getDescription().setEnabled(false);

                binding.lineChart.getXAxis().setValueFormatter(new MyAxisValueFormatter());

                binding.lineChart.setData(data);

                binding.lineChart.setMinimumWidth(3000);
                binding.lineChart.animateX(1500);
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void setCurrentTemperature(String temp) {
        binding.temp.setText(temp);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onDestroyView() {
        binding = null;
        presenter.unsubscribe();
        super.onDestroyView();
    }
}