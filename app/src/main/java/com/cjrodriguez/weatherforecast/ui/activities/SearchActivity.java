package com.cjrodriguez.weatherforecast.ui.activities;

import static com.cjrodriguez.weatherforecast.util.Constants.CURRENT_COUNTRY;
import static com.cjrodriguez.weatherforecast.util.Constants.CURRENT_LOCATION_STRING;
import static com.cjrodriguez.weatherforecast.util.Constants.TRANSFER_CITY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.databinding.ActivitySearchBinding;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.model.WeatherModel;
import com.cjrodriguez.weatherforecast.mvp.presenters.SearchActivityPresenter;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.ui.adapter.LocationSearchListAdapter;

import java.util.List;

import javax.inject.Inject;

public class SearchActivity extends AppCompatActivity implements Contract.View {

    ActivitySearchBinding binding;
    LocationSearchListAdapter arrayAdapter;

    String currentCity;

    LocationSearchListAdapter.OnItemClickListener listener;

    Contract.Presenter presenter;

    @Inject
    WeatherRepository weatherRepository;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        ((BaseApplication) getApplicationContext()).appComponent.inject(this);
        View decorView = getWindow().getDecorView();
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);

        binding.locationSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.locationSearch, InputMethodManager.SHOW_IMPLICIT);

        presenter = new SearchActivityPresenter(this,
                new WeatherModel(weatherRepository));

        String uni = presenter.readLocationCountry();

        setSupportActionBar(binding.toolbar);
        ActionBar appBar = getSupportActionBar();
        listener = location -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(TRANSFER_CITY, location.getName().replace(CURRENT_LOCATION_STRING, "").trim());
            startActivity(intent);
            finish();
        };
        arrayAdapter = new LocationSearchListAdapter(List.of(new Location(uni)), listener);
        binding.simpleListView.setAdapter(arrayAdapter);
        if(appBar != null){
            appBar.setDisplayHomeAsUpEnabled(true);
            appBar.setDisplayShowHomeEnabled(true);
        }

        binding.locationSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {return false;}

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty()){
                    currentCity = newText.trim();
                    presenter.searchLocation(newText.trim());
                } else {
                    arrayAdapter = new LocationSearchListAdapter(List.of(new Location(uni)), listener);
                    binding.simpleListView.setAdapter(arrayAdapter);
                }
                return false;
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (currentCity != null){
                presenter.searchLocation(currentCity);
            }
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showProgress() {
        binding.setShouldShowProgress(true);
    }

    @Override
    public void hideProgress() {
        binding.setShouldShowProgress(false);
    }

    @Override
    public void showErrorLayout(String message) {
        binding.errorText.setText(message);
        binding.simpleListView.setVisibility(View.GONE);
        binding.errorIcon.setVisibility(View.VISIBLE);
        binding.errorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorLayout() {
        binding.errorText.setVisibility(View.GONE);
        binding.errorIcon.setVisibility(View.GONE);
        binding.simpleListView.setVisibility(View.VISIBLE);
    }

    @Override
    public String getDefaultLocation() {
        return getIntent().getStringExtra(CURRENT_COUNTRY);
    }


    @Override
    public void setSearchLocationList(List<Location> locationList) {
        arrayAdapter = new LocationSearchListAdapter(locationList, listener);
        binding.simpleListView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        binding = null;
        arrayAdapter = null;
        super.onDestroy();
    }
}