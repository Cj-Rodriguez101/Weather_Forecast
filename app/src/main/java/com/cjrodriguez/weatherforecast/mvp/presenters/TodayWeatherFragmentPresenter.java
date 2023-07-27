package com.cjrodriguez.weatherforecast.mvp.presenters;

import android.util.Log;

import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;

import java.util.List;

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

        compositeDisposable.add(model.getCurrentWeatherData(location, true).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdatedWeatherData>() {
                    @Override
                    public void onSuccess(@NonNull UpdatedWeatherData updatedWeatherData) {
                        if (updatedWeatherData.getForecast().getForecastday().size() > 0) {
                            //Log.e("sictoday", updatedWeatherData.getCurrent().getCondition().getIcon());
                            mainView.setUpdatedWeatherData(updatedWeatherData);
                            mainView.setLineTodayChartData(updatedWeatherData);
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
