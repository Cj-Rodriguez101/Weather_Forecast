package com.cjrodriguez.weatherforecast.mvp.presenters;

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
        compositeDisposable.add(model.searchLocation(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Location>>() {

                    @Override
                    public void onSuccess(@NonNull List<Location> locations) {

                        mainView.hideErrorLayout();
                        mainView.setSearchLocationList(locations);
                        mainView.hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainView.setSearchLocationList(List.of(new Location(mainView.getDefaultLocation())));
                        mainView.hideProgress();
                        mainView.showErrorLayout("Make Sure Internet Is Connected");
                    }
                }));
    }

    @Override
    public void writeSelectedCountry(String country) {
        model.writeSelectedCountry(country);
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
