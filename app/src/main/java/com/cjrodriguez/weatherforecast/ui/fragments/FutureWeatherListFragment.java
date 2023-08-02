package com.cjrodriguez.weatherforecast.ui.fragments;

import static com.cjrodriguez.weatherforecast.util.Constants.SELECTED_CITY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cjrodriguez.weatherforecast.BaseApplication;
import com.cjrodriguez.weatherforecast.databinding.FragmentFutureWeatherListBinding;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.mvp.Contract;
import com.cjrodriguez.weatherforecast.mvp.model.WeatherModel;
import com.cjrodriguez.weatherforecast.mvp.presenters.FutureListFragmentPresenter;
import com.cjrodriguez.weatherforecast.repository.WeatherRepository;
import com.cjrodriguez.weatherforecast.ui.adapter.FutureListAdapter;

import java.util.List;

import javax.inject.Inject;

public class FutureWeatherListFragment extends BaseFragment implements Contract.View {

    private FragmentFutureWeatherListBinding binding;
    private FutureListAdapter adapter;

    Contract.Presenter presenter;

    @Inject
    WeatherRepository weatherRepository;
    public FutureWeatherListFragment() {
        // Required empty public constructor
    }

    public static FutureWeatherListFragment newInstance(String param1) {
        FutureWeatherListFragment fragment = new FutureWeatherListFragment();
        if(param1 != null){
            Bundle args = new Bundle();
            args.putString(SELECTED_CITY, param1);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((BaseApplication) requireActivity().getApplicationContext()).appComponent.inject(this);
    }

    @Override
    public void setFutureListData(List<Forecastday> forecastDayList) {
        adapter.submitList(forecastDayList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FutureListFragmentPresenter(this,
                new WeatherModel(weatherRepository));
        presenter.loadAllWeatherData("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFutureWeatherListBinding.inflate(inflater, container, false);

        adapter = new FutureListAdapter();
        binding.futureRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.futureRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void updateScreen() {
        presenter.loadAllWeatherData(weatherRepository.readSelectedCity());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        adapter = null;
        super.onDestroyView();
    }
}