package com.cjrodriguez.weatherforecast.fragments;

import static com.cjrodriguez.weatherforecast.util.Constants.SELECTED_CITY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.util.BaseFragment;

public class FutureWeatherListFragment extends BaseFragment {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future_weather_list, container, false);
    }

    @Override
    public void updateScreen() {

    }
}