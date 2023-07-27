package com.cjrodriguez.weatherforecast;

import static com.cjrodriguez.weatherforecast.util.Constants.CURRENT_COUNTRY;
import static com.cjrodriguez.weatherforecast.util.Constants.TRANSFER_CITY;
import static com.cjrodriguez.weatherforecast.util.Constants.WEATHER_REQUEST_CODE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cjrodriguez.weatherforecast.databinding.ActivityMainBinding;
import com.cjrodriguez.weatherforecast.fragments.FutureWeatherListFragment;
import com.cjrodriguez.weatherforecast.fragments.TodayWeatherFragment;
import com.cjrodriguez.weatherforecast.fragments.TomorrowWeatherFragment;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.presenters.MainActivityPresenter;
import com.cjrodriguez.weatherforecast.mvp.WeatherModel;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.util.BaseFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements Contract.View {

    //private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private FusedLocationProviderClient fusedLocationProviderClient;
    //private SettingsDatastore datastore;

    private DemoCollectionAdapter demoCollectionAdapter;
    Contract.Presenter presenter;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private android.location.Location currentLocation;

    //private String currentCountryName;
    private String cityName;
    ActivityMainBinding binding;
    //private final RetrofitService ln = RetrofitServiceImpl.getInstance();
//    @Inject
//    RetrofitService retrofitService;
//
//    @Inject
//    WeatherDao weatherDao;

    @Inject
    WeatherRepository weatherRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplicationContext()).appComponent.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainActivityPresenter(this,
                new WeatherModel(weatherRepository));
        cityName = presenter.readSelectedCountry();
        binding.currentLocation.setText(cityName);

        if (demoCollectionAdapter == null) {
            demoCollectionAdapter = new DemoCollectionAdapter(getSupportFragmentManager(), getLifecycle());
        }

        binding.fragContainer.setAdapter(demoCollectionAdapter);

        binding.swiperefresh.setOnRefreshListener(() -> {
            presenter.updateWeatherCache(presenter.readSelectedCountry());
            binding.swiperefresh.setRefreshing(false);
        });

        new TabLayoutMediator(binding.tabs, binding.fragContainer, (tab, position) ->
        {
            switch (position){
                case 0:
                    tab.setText("Today");
                    break;
                case 1:
                    tab.setText("Tomorrow");
                    break;
                default:
                    tab.setText("Future");
                    break;
            }
        }).attach();

        boolean isLocationGranted = checkIfLocationPermissionGranted();
        if (isLocationGranted) {
            if(cityName == null || cityName.isEmpty()){
                getCurrentLocation();
            }
        } else {
            String[] permissionArray = {android.Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(
                    this,
                    permissionArray,
                    WEATHER_REQUEST_CODE
            );
        }

        binding.openButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(CURRENT_COUNTRY, cityName);
            startActivity(intent);
        });

        binding.fragContainer.setUserInputEnabled(false);
    }

    private boolean checkIfLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //locationRequest = LocationRequest.create();
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,
                600000L).build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(@androidx.annotation.NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }

            @Override
            public void onLocationResult(@androidx.annotation.NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();
                if (currentLocation != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> address =
                                geocoder.getFromLocation(currentLocation.getLatitude(),
                                        currentLocation.getLongitude(), 1);

                        if (address.size() > 0) {
                            //Log.e("yh", address.get(0).getCountryName());
                            String name = address.get(0).getLocality();
                            if(cityName.isEmpty()){
                                presenter.writeSelectedCountry(name);
                                binding.currentLocation.setText(name);
                                presenter.updateWeatherCache(name);
                            }
                            //binding.currentLocation.setText(currentCountryName);
                            //presenter.writeSelectedCountry(currentCountryName);

                            //changed here
//                            if(!Objects.equals(presenter.readSelectedCountry(), currentCountryName)){
//                                presenter.updateWeatherCache(currentCountryName);
//                            }
//                            if(!Objects.equals(presenter.readSelectedCountry(), currentCountryName)){
//                                presenter.updateWeatherCache(currentCountryName);
//                            }

                        }
                    } catch (IOException e) {
                        Log.e("exec", e.getMessage());
                        //throw new RuntimeException(e);
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WEATHER_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }  else {
                    finish();
                }
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStop() {
        //compositeDisposable.clear();
        if(fusedLocationProviderClient != null){
            fusedLocationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("TAG", "Location Callback removed.");
                } else {
                    Log.d("TAG", "Failed to remove Location Callback.");
                }
            });
        }
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String country = intent.getStringExtra(TRANSFER_CITY);
        if(country != null){
            presenter.writeSelectedCountry(country);
            presenter.updateWeatherCache(country);
        }
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void setCurrentLocation(String city) {
        binding.currentLocation.setText(city);
    }

    class DemoCollectionAdapter extends FragmentStateAdapter {
        public DemoCollectionAdapter(@NonNull FragmentManager fragmentManager,
                                     Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            String selectedCity = presenter.readSelectedCountry();//datastore.readSelectedCity();
            switch (position) {
                case 0:
                    return TodayWeatherFragment.newInstance(selectedCity);
                case 1:
                    return TomorrowWeatherFragment.newInstance(selectedCity);
                default:
                    return FutureWeatherListFragment.newInstance(selectedCity);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateFragments() {
        binding.currentLocation.setText(cityName);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(!fragments.isEmpty()){
            for (int i = 0; i<fragments.size(); i++){
                Fragment fragment = fragments.get(i);
                if(fragment instanceof BaseFragment){
                    ((BaseFragment) fragment).updateScreen();
                }
            }
        }
    }
}