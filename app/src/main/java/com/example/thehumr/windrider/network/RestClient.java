package com.example.thehumr.windrider.network;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class RestClient {

    public static final String ACCESS_TOKEN = "access_token";

    private static RestClient instance = new RestClient();

    private StravaService stravaService;

    private WeatherService weatherService;

    public static StravaService getStravaService() {
        return instance.stravaService;
    }

    public static WeatherService getWeatherService() {
        return instance.weatherService;
    }

    public RestClient() {
        stravaService = RetrofitInstance.getRetrofitInstanceStrava().create(StravaService.class);
        weatherService = RetrofitInstance.getRetrofitInstanceWeather().create(WeatherService.class);
    }
}
