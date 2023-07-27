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

public class MainActivityPresenter implements Contract.Presenter {

    private final Contract.View mainView;
    private final Contract.Model model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainActivityPresenter(Contract.View mainView, Contract.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void loadAllWeatherData(String location) {
        //Log.e("ooo", "eer");
//        mainView.showProgress();
//        compositeDisposable.add(model.getCurrentWeatherData(location).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<UpdatedWeatherData>() {
//
//                    @Override
//                    public void onSuccess(@NonNull UpdatedWeatherData updatedWeatherData) {
//                        //mainView.setCurrentTemperature(String.valueOf(updatedWeatherData.getCurrent().getTemp_c()));
//                        //Log.e("success", "success"+ updatedWeatherData.getCurrent().getTemp_c());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e("error", e.getMessage());
//                        //mainView.setCurrentTemperature("0.0");
//                    }
//                }));
//
//        mainView.hideProgress();
    }

    @Override
    public void updateWeatherCache(String location) {
        mainView.showProgress();
        compositeDisposable.add(model.updateWeatherCache(location).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdatedWeatherData>() {
                    @Override
                    public void onSuccess(@NonNull UpdatedWeatherData updatedWeatherData) {

                        mainView.updateFragments();
                        mainView.setCurrentLocation(location);

//                        if (updatedWeatherData.getForecast().getForecastday().size() > 0) {
//                            model.clearAllWeatherData();
//                            model.insertUpdateWeatherData(updatedWeatherData.getLocation(),
//                                    updatedWeatherData.getCurrent(),
//                                    List.of(updatedWeatherData.getForecast()));
//                        }
//                        Log.e("sic", "heher");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                }));
        //model.updateWeatherCache(location);
        mainView.hideProgress();
    }

    @Override
    public void writeSelectedCountry(String country) {
        model.writeSelectedCountry(country);
    }

    @Override
    public String readSelectedCountry() {
        return model.readSelectedCountry();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


}
