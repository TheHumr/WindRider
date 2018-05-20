package com.example.thehumr.windrider.network;

import com.example.thehumr.windrider.database.dto.WeatherContainer;
import com.example.thehumr.windrider.database.table.Segment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ondraboura on 19/05/2018.
 */

public interface WeatherService {

    String API_KEY = "ea3fba167aa3173f71a4851a65cc6fd3";

    String ENDPOINT_forecast = "forecast";
    String LAT = "lat";
    String LON = "lon";
    String APPID = "APPID";

    @GET(ENDPOINT_forecast)
    Call<WeatherContainer> getWeatherByCoordinates(@Query(APPID) String appID, @Query(LAT) double lat, @Query(LON) double lon);

}

