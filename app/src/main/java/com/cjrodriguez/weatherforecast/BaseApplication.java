package com.cjrodriguez.weatherforecast;

import android.app.Application;

import com.cjrodriguez.weatherforecast.di.AppModule;
import com.cjrodriguez.weatherforecast.di.ApplicationComponent;
import com.cjrodriguez.weatherforecast.di.DaggerApplicationComponent;
//import com.cjrodriguez.weatherforecast.di.DaggerApplicationComponent;

// appComponent lives in the Application class to share its lifecycle
public class BaseApplication extends Application {
    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    // Reference to the application graph that is used across the whole app
    //public ApplicationComponent appComponent = DaggerApplicationComponent.create();

    private void initDagger() {
        appComponent = DaggerApplicationComponent.builder().appModule(new AppModule(this)).build();

        appComponent.inject(this);
    }
}
