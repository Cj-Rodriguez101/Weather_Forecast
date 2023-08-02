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
        ((BaseApplication) requireActivity().getApplicationContext())
                .appComponent.inject(this);
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
    public void setUpdatedWeatherData(WeatherData weatherData) {
        binding.setCurrentWeather(weatherData);
        List<Forecastday> forecastDayList = weatherData.getForecast().getForecastday();
        if (!forecastDayList.isEmpty()) {
            Day day = forecastDayList.get(0).getDay();
            binding.setCurrentDay(day);
        }
    }

    @Override
    public void updateScreen() {
        presenter.loadAllWeatherData(weatherRepository.readSelectedCity());
    }

    @Override
    public void setLineTodayChartData(WeatherData weatherData) {
        List<Forecastday> foreCastDayList = weatherData.getForecast().getForecastday();

        if(!foreCastDayList.isEmpty()){
            BindingAdapters.setLineChartData(binding.lineChart, foreCastDayList.get(0));
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        presenter.unsubscribe();
        super.onDestroyView();
    }
}