package com.example.thehumr.windrider.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class RetrofitInstance {

    private static Retrofit retrofitStrava;
    private static Retrofit retrofitWeather;
    public static final String BASE_URL_STRAVA = "https://www.strava.com/api/v3/";
    public static final String BASE_URL_OPEN_WEATHER_MAP = "https://api.openweathermap.org/data/2.5/";

    public static Retrofit getRetrofitInstanceStrava() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addNetworkInterceptor(logging);
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        if (retrofitStrava == null) {
            retrofitStrava = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_STRAVA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofitStrava;
    }

    public static Retrofit getRetrofitInstanceWeather() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addNetworkInterceptor(logging);
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        if (retrofitWeather == null) {
            retrofitWeather = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL_OPEN_WEATHER_MAP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofitWeather;
    }
}