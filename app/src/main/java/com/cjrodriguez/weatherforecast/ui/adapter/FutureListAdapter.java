package com.cjrodriguez.weatherforecast.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cjrodriguez.weatherforecast.databinding.ListItemRvBinding;
import com.cjrodriguez.weatherforecast.model.Forecastday;

public class FutureListAdapter extends ListAdapter<Forecastday,
        FutureListAdapter.ForecastdayViewHolder> {


    public FutureListAdapter() {
        super(new DiffCallback());
    }

    @NonNull
    @Override
    public ForecastdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastdayViewHolder(ListItemRvBinding.inflate(
                LayoutInflater.from(parent.getContext())
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastdayViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ForecastdayViewHolder extends RecyclerView.ViewHolder {

        private final ListItemRvBinding binding;

        public ForecastdayViewHolder(ListItemRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Forecastday updatedWeatherData){
            binding.setForecastDay(updatedWeatherData);
            binding.executePendingBindings();
        }
    }

    protected static class DiffCallback extends DiffUtil.ItemCallback<Forecastday>{

        @Override
        public boolean areItemsTheSame(@NonNull Forecastday oldItem, @NonNull Forecastday newItem) {
            return oldItem.getDate_epoch() == newItem.getDate_epoch();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Forecastday oldItem, @NonNull Forecastday newItem) {
            return oldItem.equals(newItem);
        }
    }
}
