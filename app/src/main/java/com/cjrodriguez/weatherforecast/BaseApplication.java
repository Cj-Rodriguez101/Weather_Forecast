package com.cjrodriguez.weatherforecast;

import android.app.Application;

import com.cjrodriguez.weatherforecast.di.AppModule;
import com.cjrodriguez.weatherforecast.di.ApplicationComponent;
import com.cjrodriguez.weatherforecast.di.DaggerApplicationComponent;

public class BaseApplication extends Application {
    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerApplicationComponent.builder().appModule(new AppModule(this)).build();

        appComponent.inject(this);
    }
}
