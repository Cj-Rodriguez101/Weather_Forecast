package com.cjrodriguez.weatherforecast.datasource.network;

import static com.cjrodriguez.weatherforecast.util.Constants.BASE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//public class LocationNetwork {
//    private static LocationNetwork instance;
//    private static RetrofitService retrofitService;
//
//    private LocationNetwork() {}
//
//    public static synchronized LocationNetwork getInstance()
//    {
//        if (instance == null){
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(0, TimeUnit.SECONDS)
//                    .writeTimeout(0, TimeUnit.SECONDS)
//                    .readTimeout(0, TimeUnit.SECONDS)
//                    .build();
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(MoshiConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//            retrofitService = retrofit.create(RetrofitService.class);
//
//            instance = new LocationNetwork();
//        }
//
//        return instance;
//    }
//}

public class RetrofitServiceImpl {
    private static RetrofitService instance;

    private RetrofitServiceImpl() {}
    public static synchronized RetrofitService getInstance()
    {
        if (instance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(0, TimeUnit.SECONDS)
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            instance = retrofit.create(RetrofitService.class);
        }

        return instance;
    }
}