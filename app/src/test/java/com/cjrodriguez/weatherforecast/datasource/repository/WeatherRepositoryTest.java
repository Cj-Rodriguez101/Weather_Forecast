package com.cjrodriguez.weatherforecast.datasource.repository;

import static com.cjrodriguez.weatherforecast.util.Constants.TEST_CITY;
import static org.junit.Assert.assertEquals;
import com.cjrodriguez.weatherforecast.datasource.cache.WeatherDao;
import com.cjrodriguez.weatherforecast.datasource.network.RetrofitService;
import com.cjrodriguez.weatherforecast.datastore.SettingsDatastore;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.WeatherData;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.repository.WeatherRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryTest {

    @Mock
    WeatherDao weatherDao;

    @Mock
    SettingsDatastore datastore;

    @Mock
    RetrofitService retrofitService;

    WeatherRepository weatherRepository;

    @Before
    public void setup() {
        weatherRepository = new WeatherRepositoryImpl(retrofitService, weatherDao, datastore);
    }

    @Test
    public void testIfLocationsSearchedIsPresent_ExpectSuccess() {
        List<Location> expectedLocationList = List.of(new Location(TEST_CITY));

        Mockito.when(retrofitService.searchLocations(TEST_CITY))
                .thenReturn(Single.just(expectedLocationList));

        List<Location> actualLocationList = weatherRepository
                .getSearchLocations(TEST_CITY).blockingGet();

        assertEquals(expectedLocationList, actualLocationList);
    }

    @Test
    public void testIfCurrentWeatherDataIsRetrieved_ExpectSuccess() {
        Location expectedLocation = new Location(TEST_CITY);

        Mockito.when(weatherDao.getCurrentWeatherData(TEST_CITY))
                .thenReturn(new WeatherData(expectedLocation, null, null));

        Location actualLocation = weatherRepository.getWeatherForecastFromCache(TEST_CITY,
                true).blockingGet().getLocation();

        assertEquals(expectedLocation, actualLocation);
    }
}
