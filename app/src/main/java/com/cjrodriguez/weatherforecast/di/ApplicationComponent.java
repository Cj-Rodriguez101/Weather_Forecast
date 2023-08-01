package com.cjrodriguez.weatherforecast.di;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.ui.activities.MainActivity;
import com.cjrodriguez.weatherforecast.ui.activities.SearchActivity;
import com.cjrodriguez.weatherforecast.ui.fragments.FutureWeatherListFragment;
import com.cjrodriguez.weatherforecast.ui.fragments.TodayWeatherFragment;
import com.cjrodriguez.weatherforecast.ui.fragments.TomorrowWeatherFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {
    void inject(BaseApplication application);
    void inject(MainActivity mainActivity);
    void inject(SearchActivity searchActivity);
    void inject(TodayWeatherFragment todayWeatherFragment);
    void inject(TomorrowWeatherFragment tomorrowWeatherFragment);

    void inject(FutureWeatherListFragment futureWeatherListFragment);
}