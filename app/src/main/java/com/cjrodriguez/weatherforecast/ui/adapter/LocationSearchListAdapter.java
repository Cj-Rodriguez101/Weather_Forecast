package com.cjrodriguez.weatherforecast.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cjrodriguez.weatherforecast.databinding.ListItemLvBinding;
import com.cjrodriguez.weatherforecast.model.Location;

import java.util.List;

//https://dbremes.wordpress.com/2017/11/12/data-binding-android-listviews/
public class LocationSearchListAdapter extends BaseAdapter {
    private final List<Location> locations;
    private final OnItemClickListener listeners;
    private LayoutInflater mLayoutInflater;

    public LocationSearchListAdapter(List<Location> demoModels, OnItemClickListener listener) {
        locations = demoModels;
        listeners = listener;
    }

    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int i) {
        return locations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        View result = view;
        ListItemLvBinding binding;
        if (result == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) viewGroup.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            binding = ListItemLvBinding.inflate(
                    mLayoutInflater, viewGroup, false);
            result = binding.getRoot();
            result.setTag(binding);
        }
        else {
            binding = (ListItemLvBinding) result.getTag();
        }
        result.setOnClickListener(v -> listeners.onItemClick((Location) getItem(position)));
        //result.setOnClickListener(listeners);
        binding.setSingleLocation(locations.get(position));
        return result;
    }

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }
}
