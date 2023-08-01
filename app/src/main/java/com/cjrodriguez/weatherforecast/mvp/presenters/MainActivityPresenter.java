package com.cjrodriguez.weatherforecast.mvp.presenters;

import android.util.Log;

import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.mvp.Contract;

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
    public void updateWeatherCache(String location) {
        mainView.showProgress();
        compositeDisposable.add(model.updateWeatherCache(location).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherData>() {
                    @Override
                    public void onSuccess(@NonNull WeatherData weatherData) {

                        if(location.isEmpty()){
                            mainView.showErrorLayout("No Location Selected");
                        } else {
                            mainView.hideErrorLayout();
                        }
                        mainView.updateFragments();
                        mainView.setCurrentLocation(location);
                        mainView.hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainView.hideProgress();
                        mainView.showErrorLayout("Make Sure Internet Is Connected");
                        Log.e("error", e.getMessage()); //Crashlytics for prod
                    }


                }));
        //model.updateWeatherCache(location);
//        mainView.hideProgress();
    }

    @Override
    public void writeSelectedCountry(String country) {
        model.writeSelectedCountry(country);
    }

    @Override
    public void writeLocationCountry(String country) {
        model.writeLocationCountry(country);
    }

    @Override
    public String readSelectedCountry() {
        return model.readSelectedCountry();
    }

    @Override
    public String readLocationCountry() {
        return model.readLocationCountry();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


}
