package com.cjrodriguez.weatherforecast.repository;

import android.util.Log;

import com.cjrodriguez.weatherforecast.BuildConfig;
import com.cjrodriguez.weatherforecast.datasource.cache.WeatherDao;
import com.cjrodriguez.weatherforecast.datastore.SettingsDatastore;
import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.datasource.network.RetrofitService;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private final RetrofitService retrofitService;
    private final WeatherDao weatherDao;
    private final SettingsDatastore datastore;
    //private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeatherRepositoryImpl(RetrofitService retrofitService, WeatherDao weatherDao, SettingsDatastore datastore) {
        this.retrofitService = retrofitService;
        this.weatherDao = weatherDao;
        this.datastore = datastore;
    }

    @Override
    public Single<List<Location>> getSearchLocations(String query) {
        return retrofitService.searchLocations(BuildConfig.API_KEY, query);
    }

    @Override
    public Single<WeatherData> getCurrentWeatherData(String query) {
        //return null;
        return retrofitService.getCurrentWeatherData(BuildConfig.API_KEY, query, "no");
    }

    @Override
    public Single<UpdatedWeatherData> getWeatherForecastFromCache(String query, boolean isToday) {
        return Single.fromCallable(() -> {
            //try {
            if(isToday){
                return weatherDao.getCurrentWeatherData(query);
            } else {
                return weatherDao.getTomorrowWeatherData(query);
            }
//            } catch (Exception e) {
//                throw new RuntimeException();
//            }
        });
        //return Single.just(weatherDao.getCurrentWeatherData(query));
        //return retrofitService.getWeatherDataForecast(BuildConfig.API_KEY, query, "no", 3, "no");
    }

    @Override
    public Single<UpdatedWeatherData> updateWeatherData(String query) {

        return retrofitService
                .getWeatherDataForecast(BuildConfig.API_KEY, query,
                        "no", 3, "no").doOnSuccess((weatherData -> {
                          if(weatherData.getForecast().getForecastday().size()>0){
                              weatherDao.clearAllData();
                              weatherDao.insertAllData(weatherData.getLocation(),
                                      weatherData.getCurrent(), List.of(weatherData.getForecast()));
                          }
                } )).doOnError((er->{
                    Log.e("er", er.getMessage());
                }));

//        Single<UpdatedWeatherData> weatherDataSingle = retrofitService
//                .getWeatherDataForecast(BuildConfig.API_KEY, query, "no", 3, "no");
//        compositeDisposable.add(
//                weatherDataSingle.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<UpdatedWeatherData>() {
//
//                    @Override
//                    public void onSuccess(@NonNull UpdatedWeatherData updatedWeatherData) {
//                        //arrayAdapter = new ArrayAdapter<Location>(getApplicationContext(), R.layout.list_item, locations);
//
//                        if (updatedWeatherData.getForecast().getForecastday().size() > 0) {
//                            weatherDao.clearAllData();
//                            weatherDao.insertAllData(List.of(updatedWeatherData.getLocation()),
//                                    updatedWeatherData.getCurrent(), List.of(updatedWeatherData.getForecast()));
//                            Log.e("success", "success completed");
//                        }
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

        //compositeDisposable.clear();
    }

    @Override
    public void writeWeatherCountryName(String country) {
        datastore.writeSelectedCity(country);
    }

    @Override
    public String readWeatherCountryName() {
        return datastore.readSelectedCity();
    }

    @Override
    public void clearAllData() {
        weatherDao.clearAllData();
    }

    @Override
    public void insertAllData(Location location, Current current, List<Forecast> forecastList) {
        weatherDao.insertAllData(location, current, forecastList);
    }

//    @Override
//    public Single<Current> getCurrentData() {
//
//        return weatherDao.getCurrentSingleData();
//        //compositeDisposable.add(weatherDao.getCurrentData().subscribeOn(Schedulers.io()).);
//    }
}
