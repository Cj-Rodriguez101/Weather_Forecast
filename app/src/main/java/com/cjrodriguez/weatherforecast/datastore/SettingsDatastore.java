package com.cjrodriguez.weatherforecast.datastore;

import android.content.Context;
import android.util.Log;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingsDatastore {
    private final RxDataStore<Preferences> dataStore;

    public SettingsDatastore(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    Preferences.Key<String> SELECTED_CITY = PreferencesKeys.stringKey("selected_city");

    public String readSelectedCity(){
        Flowable<String> exampleCounterFlow =
                dataStore.data().map(prefs -> prefs.get(SELECTED_CITY));

        try{
            String selectedCity = exampleCounterFlow.blockingFirst();

            return selectedCity != null ? selectedCity : "";
        } catch (Exception exception){
            Log.e("TAG", exception.getMessage());
            return "";
        }
    }

    public void writeSelectedCity(String city){
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(SELECTED_CITY, city != null ? city : "");
            return Single.just(mutablePreferences);
        });
    }
}
