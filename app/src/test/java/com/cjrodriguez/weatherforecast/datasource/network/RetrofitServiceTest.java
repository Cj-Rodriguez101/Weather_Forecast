package com.cjrodriguez.weatherforecast.datasource.network;

import static com.cjrodriguez.weatherforecast.util.Constants.NO;
import static com.cjrodriguez.weatherforecast.util.Constants.TEST_CITY;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
public class RetrofitServiceTest {

    RetrofitService retrofitService;
    @Before
    public void setUp(){
        retrofitService = RetrofitServiceImpl.getInstance();
    }

    @Test
    public void searchLocationBasedOnQuery_ExpectSuccess(){
        List<Location> locationList = retrofitService.searchLocations(TEST_CITY).blockingGet();
        boolean doesContainSearchedCity = false;

        for (Location location : locationList) {
            if (location.getName().equals(TEST_CITY)) {
                doesContainSearchedCity = true;
                break;
            }
        }
        assert(doesContainSearchedCity);
    }

    @Test
    public void searchCurrentBasedOnQuery_ExpectSuccess(){
        WeatherData weatherData = retrofitService.getWeatherDataForecast(TEST_CITY, NO,
                1, NO).blockingGet();

        Location location = weatherData.getLocation();
        boolean doesContainSearchedCity = location.getName().equals(TEST_CITY);

        assert(doesContainSearchedCity);
    }
}
