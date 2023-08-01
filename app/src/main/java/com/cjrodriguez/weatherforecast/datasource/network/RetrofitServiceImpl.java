package com.cjrodriguez.weatherforecast.datasource.network;

import static com.cjrodriguez.weatherforecast.util.Constants.BASE_URL;
import static com.cjrodriguez.weatherforecast.util.Constants.KEY;

import com.cjrodriguez.weatherforecast.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(KEY, BuildConfig.API_KEY).build();
                        return chain.proceed(request);
                    })
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