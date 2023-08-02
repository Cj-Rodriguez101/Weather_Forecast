package com.cjrodriguez.weatherforecast.datasource.cache;

import static com.cjrodriguez.weatherforecast.util.Constants.TEST_CITY;
import static org.mockito.Mockito.verify;

import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherDaoTest {
    WeatherDao weatherDao;

    @Before
    public void setup(){
        weatherDao = Mockito.spy(new WeatherDao() {
            @Override
            Location getLocationData(String query) {
                return null;
            }

            @Override
            Current getCurrentSingleData() {
                return null;
            }

            @Override
            public List<Forecast> getForecastData() {
                return List.of(new Forecast(90, List.of()));
            }

            @Override
            void deleteLocationData() {}

            @Override
            void deleteCurrentWeatherData() {}

            @Override
            void deleteForecastData() {}

            @Override
            void insertLocation(Location locationList) {}

            @Override
            void insertCurrent(Current Current) {}

            @Override
            void insertForecast(List<Forecast> forecastList) {}
        });

    }

    @Test
    public void testIfAllDataIsClearedOnTransaction_ExpectSuccess() {

        weatherDao.clearAllData();

        verify(weatherDao).deleteCurrentWeatherData();
        verify(weatherDao).deleteLocationData();
        verify(weatherDao).deleteForecastData();
    }

    //no need for tommorrow and future as they are very similar
    @Test
    public void testAndGetCurrentWeatherData_ExpectSuccess() {
        weatherDao.getCurrentWeatherData(TEST_CITY);

        verify(weatherDao).getLocationData(TEST_CITY);
        verify(weatherDao).getCurrentSingleData();
        verify(weatherDao).getForecastData();

    }
}
