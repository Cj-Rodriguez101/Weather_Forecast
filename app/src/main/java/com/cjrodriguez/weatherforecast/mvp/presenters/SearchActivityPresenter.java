package com.cjrodriguez.weatherforecast.mvp.presenters;

import android.util.Log;

import com.cjrodriguez.weatherforecast.LocationSearchListAdapter;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.mvp.Contract;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivityPresenter implements Contract.Presenter {

    private final Contract.View mainView;
    private final Contract.Model model;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchActivityPresenter(Contract.View mainView, Contract.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void searchLocation(String query) {
        mainView.showProgress();
        //model.searchLocation(mainView.getSearchLocation());
        compositeDisposable.add(model.searchLocation(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Location>>() {

                    @Override
                    public void onSuccess(@NonNull List<Location> locations) {
                            //arrayAdapter = new ArrayAdapter<Location>(getApplicationContext(), R.layout.list_item, locations);
                        mainView.setSearchLocationList(locations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainView.setSearchLocationList(List.of(new Location(mainView.getDefaultLocation())));
                    }
                }));
        mainView.hideProgress();
    }

    @Override
    public void writeSelectedCountry(String country) {
        model.writeSelectedCountry(country);
    }

    //    @Override
//    public void setCurrentDefaultLocation(String query) {
//        mainView.setDefaultLocation(query);
//    }

//    @Override
//    public void onDestroy() {
//        mainView = null;
//    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
