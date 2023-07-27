package com.cjrodriguez.weatherforecast.di;

import android.content.Context;

import androidx.room.Room;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.datasource.cache.WeatherDao;
import com.cjrodriguez.weatherforecast.datasource.cache.WeatherDatabase;
import com.cjrodriguez.weatherforecast.datastore.SettingsDatastore;
import com.cjrodriguez.weatherforecast.datasource.network.RetrofitService;
import com.cjrodriguez.weatherforecast.datasource.network.RetrofitServiceImpl;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.repository.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final BaseApplication application;

    public AppModule(BaseApplication app) {
        this.application = app;
    }

//    private final Context context;
//
//    public AppModule(Context context) {
//        this.context = context;
//    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    RetrofitService providesRetrofitService(){
        return RetrofitServiceImpl.getInstance();
    }

    @Provides
    @Singleton
    WeatherDatabase providesWeatherDatabase(Context context){
        return Room.databaseBuilder(
                context, WeatherDatabase.class, "Weather.db"
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    WeatherDao providesWeatherDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.weatherDao();
    }

    @Provides
    @Singleton
    SettingsDatastore providesSettingsDatastore(Context context){
        return new SettingsDatastore(context);
    }

    @Provides
    @Singleton
    WeatherRepository providesWeatherRepository(RetrofitService retrofitService,
                                                WeatherDao weatherDao, SettingsDatastore settingsDatastore){
        return new WeatherRepositoryImpl(retrofitService, weatherDao,settingsDatastore);
    }
}
