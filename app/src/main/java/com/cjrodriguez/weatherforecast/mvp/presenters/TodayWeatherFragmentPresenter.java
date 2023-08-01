package com.cjrodriguez.weatherforecast.mvp.presenters;

import android.util.Log;

import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TodayWeatherFragmentPresenter implements Contract.Presenter {

    private final Contract.View mainView;
    private final Contract.Model model;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TodayWeatherFragmentPresenter(Contract.View mainView, Contract.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void loadAllWeatherData(String location) {
        mainView.showProgress();

        compositeDisposable.add(model.getCurrentWeatherData(location, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherData>() {
                    @Override
                    public void onSuccess(@NonNull WeatherData weatherData) {
                        if (weatherData.getForecast().getForecastday().size() > 0) {
                            mainView.setUpdatedWeatherData(weatherData);
                            mainView.setLineTodayChartData(weatherData);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                }));

        mainView.hideProgress();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
