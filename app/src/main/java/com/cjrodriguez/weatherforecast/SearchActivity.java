package com.cjrodriguez.weatherforecast;

import static com.cjrodriguez.weatherforecast.util.Constants.CURRENT_COUNTRY;
import static com.cjrodriguez.weatherforecast.util.Constants.TRANSFER_CITY;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.cjrodriguez.weatherforecast.databinding.ActivitySearchBinding;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.presenters.SearchActivityPresenter;
import com.cjrodriguez.weatherforecast.mvp.WeatherModel;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

public class SearchActivity extends AppCompatActivity implements Contract.View {

    ActivitySearchBinding binding;
    LocationSearchListAdapter arrayAdapter;

    LocationSearchListAdapter.OnItemClickListener listener;
    //TextWatcher textWatcher;

    Contract.Presenter presenter;

    @Inject
    WeatherRepository weatherRepository;

//    @Inject
//    RetrofitService retrofitService;
//
//    @Inject
//    WeatherDao weatherDao;

    //String uni;
    //private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    //private final RetrofitService ln = RetrofitServiceImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getApplicationContext()).appComponent.inject(this);
        String uni = getIntent().getStringExtra(CURRENT_COUNTRY);
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
        //this.setDefaultLocation(uni);
        //presenter.setCurrentDefaultLocation(uni);
        setSupportActionBar(binding.toolbar);
        ActionBar appBar = getSupportActionBar();
        listener = location -> {
            //presenter.writeSelectedCountry(location.getName());
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(TRANSFER_CITY, location.getName());
            startActivity(intent);
            finish();
            //Toast.makeText(getApplicationContext(), location.getName(), Toast.LENGTH_SHORT).show();
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
                    presenter.searchLocation(newText.trim());
                }
                return false;
            }
        });

//        binding.locationSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                doMySearch(s.toString());
//            }
//        });
    }

//    private void doMySearch(String query) {
//        compositeDisposable.add(ln.searchAndNewCharactersWithPage(BuildConfig.API_KEY, query)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Location>>() {
//
//                    @Override
//                    public void onSuccess(@NonNull List<Location> locations) {
//                        if(arrayAdapter != null){
//                        //arrayAdapter = new ArrayAdapter<Location>(getApplicationContext(), R.layout.list_item, locations);
//                            arrayAdapter = new LocationSearchListAdapter(locations);
//                            binding.simpleListView.setAdapter(arrayAdapter);
//                            Log.e("successfully saw", String.valueOf(locations.size()));
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        arrayAdapter = new LocationSearchListAdapter(List.of(new Location(uni)));
//                        binding.simpleListView.setAdapter(arrayAdapter);
//                        Log.e("successfully saw", "here"+ e.getMessage());
//                    }
//                }));
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        //compositeDisposable.clear();
//        if(binding != null){
//            binding.locationSearch.removeTextChangedListener(textWatcher);
//        }
        //textWatcher = null;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        binding = null;
        arrayAdapter = null;
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

//    @Override
//    public String getSearchLocation() {
//        return null;
//    }

    @Override
    public String getDefaultLocation() {
        return getIntent().getStringExtra(CURRENT_COUNTRY);
    }

//    @Override
//    public void setDefaultLocation(String query) {
//        //presenter.setCurrentDefaultLocation(query);
//    }

//    @Override
//    public void setCurrentTemperature() {
//
//    }
//
//    @Override
//    public void setTomorrowTemperature() {
//
//    }
//
//    @Override
//    public void setCurrentTempImage() {
//
//    }

    @Override
    public void setSearchLocationList(List<Location> locationList) {
        arrayAdapter = new LocationSearchListAdapter(locationList, listener);
        binding.simpleListView.setAdapter(arrayAdapter);
    }
}