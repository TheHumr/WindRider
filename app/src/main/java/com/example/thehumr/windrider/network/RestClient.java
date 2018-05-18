package com.example.thehumr.windrider.network;

import retrofit2.Retrofit;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class RestClient {

    public static final String ACCESS_TOKEN = "access_token";

    private static RestClient instance = new RestClient();

    private StravaService stravaService;

    public static StravaService getStravaService() {
        return instance.stravaService;
    }

    public RestClient() {
        stravaService = RetrofitInstance.getRetrofitInstance().create(StravaService.class);
    }
}
