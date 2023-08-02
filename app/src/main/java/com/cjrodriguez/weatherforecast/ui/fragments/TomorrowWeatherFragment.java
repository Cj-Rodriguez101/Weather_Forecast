package com.cjrodriguez.weatherforecast.ui.fragments;

import static com.cjrodriguez.weatherforecast.util.Constants.SELECTED_CITY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.databinding.FragmentTomorrowWeatherBinding;
import com.cjrodriguez.weatherforecast.model.Day;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.model.WeatherModel;
import com.cjrodriguez.weatherforecast.mvp.presenters.TomorrowWeatherFragmentPresenter;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.ui.BindingAdapters;

import java.util.List;

import javax.inject.Inject;

public class TomorrowWeatherFragment extends BaseFragment implements Contract.View {

    FragmentTomorrowWeatherBinding binding;

    Contract.Presenter presenter;

    @Inject
    WeatherRepository weatherRepository;
    public TomorrowWeatherFragment() {
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
        ((BaseApplication) requireActivity().getApplicationContext())
                .appComponent.inject(this);
    }

    @Override
    public void setUpdatedWeatherData(WeatherData weatherData) {
        binding.setTomorrowWeather(weatherData);
        List<Forecastday> forecastDayList = weatherData.getForecast().getForecastday();
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
        String location = weatherRepository.readSelectedCity();
        if (!location.isEmpty()) {
            presenter.loadAllWeatherData(location);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTomorrowWeatherBinding.inflate(inflater, container, false);
        binding.lineChart.setTouchEnabled(false);
        return binding.getRoot();
    }

    @Override
    public void updateScreen() {
        presenter.loadAllWeatherData(presenter.readSelectedCountry());
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