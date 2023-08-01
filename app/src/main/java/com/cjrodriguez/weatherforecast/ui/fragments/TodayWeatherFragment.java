package com.cjrodriguez.weatherforecast.ui.fragments;

import static com.cjrodriguez.weatherforecast.util.Constants.SELECTED_CITY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.databinding.FragmentTodayWeatherBinding;
import com.cjrodriguez.weatherforecast.model.Day;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.model.WeatherModel;
import com.cjrodriguez.weatherforecast.mvp.presenters.TodayWeatherFragmentPresenter;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.ui.BindingAdapters;

import java.util.List;

import javax.inject.Inject;

public class TodayWeatherFragment extends BaseFragment implements Contract.View {

    FragmentTodayWeatherBinding binding;
    Contract.Presenter presenter;
    @Inject
    WeatherRepository weatherRepository;

    boolean isCurrentLocation;

    public TodayWeatherFragment() {
        // Required empty public constructor
    }

    public static TodayWeatherFragment newInstance(String param1) {
        TodayWeatherFragment fragment = new TodayWeatherFragment();
        if (param1 != null) {
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
    public void setUpdatedWeatherData(WeatherData weatherData) {
        binding.setCurrentWeather(weatherData);
        List<Forecastday> forecastDayList = weatherData.getForecast().getForecastday();
        if (!forecastDayList.isEmpty()) {
            Day day = forecastDayList.get(0).getDay();
            binding.setCurrentDay(day);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TodayWeatherFragmentPresenter(this,
                new WeatherModel(weatherRepository));
        String location = weatherRepository.readSelectedCity();
        if (!location.isEmpty()) {
            presenter.loadAllWeatherData(location);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTodayWeatherBinding.inflate(inflater, container, false);
        binding.lineChart.setTouchEnabled(false);
        return binding.getRoot();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
    }

//    @Override
//    public void setCurrentTemperature(String temp) {
//        binding.temp.setText(temp);
//    }

    @Override
    public void updateScreen() {
        presenter.loadAllWeatherData(weatherRepository.readSelectedCity());
    }

//    @Override
//    public void writeIsLocationCity(boolean isLocationCity) {
//        isCurrentLocation = isLocationCity;
//        binding.setIsCurrentLocationSelected(isLocationCity);
//    }

    @Override
    public void setLineTodayChartData(WeatherData weatherData) {
//        ArrayList<Entry> values = new ArrayList<>();
//        ArrayList<Entry> values1 = new ArrayList<>();

        List<Forecastday> foreCastDayList = weatherData.getForecast().getForecastday();

        if(!foreCastDayList.isEmpty()){
            BindingAdapters.setLineChartData(binding.lineChart, foreCastDayList.get(0));
        }

//        if (!foreCastDayList.isEmpty()) {
//
//            int colorToSet = WHITE;
//
//            int nightModeFlags =
//                    getContext().getResources().getConfiguration().uiMode &
//                            Configuration.UI_MODE_NIGHT_MASK;
//            switch (nightModeFlags) {
//
//                case Configuration.UI_MODE_NIGHT_NO:
//                    colorToSet = BLACK;
//                    break;
//            }
//
//            Forecastday forecastday = foreCastDayList.get(0);
//            List<Hour> hourList = forecastday.getHour();
//            if (!hourList.isEmpty()) {
//                for (int i = 0; i < hourList.size(); i++) {
//                    float hour = Float.parseFloat(hourList.get(i).getTime()
//                            .substring(11, 13).replaceFirst("^0+(?!$)", ""));
//                    values.add(new Entry(hour, (float) hourList.get(i).getTemp_c()));
//                }
//
//                Util util = new Util();
//                for (int i = 0; i < hourList.size(); i++) {
//                    float hour = Float.parseFloat(hourList.get(i).getTime()
//                            .substring(11, 13).replaceFirst("^0+(?!$)", ""));
//                    String dayOrNight = hourList.get(i).getCondition().getIcon().split("/")[5];
//                    String no = hourList.get(i).getCondition().getIcon()
//                            .split("/")[6].replace(".png", "");
//
//                    values1.add(new Entry(hour, -36, ResourcesCompat
//                            .getDrawable(getContext().getResources(),
//                                    util.getSpecificDrawable(dayOrNight, no), getContext().getTheme())));
//                }
//
//                LineDataSet set2 = new LineDataSet(values1, "DataSet 2");
//                set2.setAxisDependency(YAxis.AxisDependency.LEFT);
//                set2.setValueFormatter(
//
//                        new ValueFormatter() {
//                    @Override
//                    public String getFormattedValue(float value) {
//                        return "";
//                    }
//                });
//
//                LineDataSet set1 = new LineDataSet(values, "DataSet 1");
//                //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//                set1.setValueFormatter(new DefaultValueFormatter(0));
//                set1.setDrawFilled(false);
//                set1.setValueTextColor(colorToSet);
//
//                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//                set2.enableDashedLine(0f, 1f, 0f);
//                dataSets.add(set1); // add the data sets
//
//                dataSets.add(set2);
//
//                LineData data = new LineData(dataSets);
//
//                binding.lineChart.getAxisLeft().setAxisLineColor(TRANSPARENT);
//                binding.lineChart.getAxisRight().setAxisLineColor(TRANSPARENT);
//                binding.lineChart.getAxisLeft().setAxisMinimum(-80);
//
//                binding.lineChart.getAxisRight().setDrawGridLines(false);
//                binding.lineChart.getAxisLeft().setDrawGridLines(false);
//                binding.lineChart.getXAxis().setDrawGridLines(false);
//
//                binding.lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                binding.lineChart.getXAxis().setLabelCount(24);
//                binding.lineChart.getXAxis().setTextColor(colorToSet);
//                binding.lineChart.getLegend().setEnabled(false);
//                binding.lineChart.getAxisLeft().setDrawLabels(false);
//                binding.lineChart.getAxisRight().setDrawLabels(false);
//                binding.lineChart.getDescription().setEnabled(false);
//
//                binding.lineChart.getXAxis().setValueFormatter(new MyAxisValueFormatter());
//
//                binding.lineChart.setData(data);
//
//                binding.lineChart.setMinimumWidth(3000);
//                binding.lineChart.animateX(1500);
//            }
//        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        presenter.unsubscribe();
        super.onDestroyView();
    }
}