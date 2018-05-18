package com.example.thehumr.windrider.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.thehumr.windrider.app.MyApplication;
import com.example.thehumr.windrider.database.dto.SegmentContainer;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.enums.State;
import com.example.thehumr.windrider.event.SegmentLoadEvent;
import com.example.thehumr.windrider.network.RestClient;
import com.samsandberg.stravaauthenticator.StravaAuthenticateActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ondraboura on 28/12/2017.
 */

public class SegmentsLoadService extends IntentService {

    public static State state = State.LOAD_SUCCESS;

    public SegmentsLoadService() {
        super("SegmentsLoadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EventBus.getDefault().post(new SegmentLoadEvent(State.LOADING));
        fetchSegments();
        EventBus.getDefault().post(new SegmentLoadEvent(state));
    }

    private void fetchSegments() {
        Call<List<Segment>> segmentCall = RestClient.getStravaService().getStarredSegments(StravaAuthenticateActivity.getStravaAccessToken(getApplicationContext()));

        segmentCall.enqueue(new Callback<List<Segment>>() {
            @Override
            public void onResponse(Call<List<Segment>> call, Response<List<Segment>> response) {
                if (response.body() != null) {
                    for (Segment segment : response.body()) {
                        segment.save();
                    }
                    state = State.LOAD_SUCCESS;
                } else {
                    state = State.LOAD_SUCCESS_EMPTY;
                }
            }

            @Override
            public void onFailure(Call<List<Segment>> call, Throwable t) {
                state = State.LOAD_FAILURE;
            }
        });
    }

    public static JSONObject getJsonObject(final String urlString) throws IOException, JSONException {
        final StringBuilder jsonResult = new StringBuilder();
        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());

            int read;
            char[] buff = new char[1024];

            while ((read = in.read(buff)) != -1) {
                jsonResult.append(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject(jsonResult.toString());
    }

    public static void getAthlete(Context context) {
        final String url = "https://www.strava.com/api/v3/athlete?access_token=" + StravaAuthenticateActivity.getStravaAccessToken(context);
        final JSONObject[] jsonObject = {null};
        final StringBuilder jsonResult = new StringBuilder();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    jsonObject[0] = SegmentsLoadService.getJsonObject(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();

//            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
//            JSONArray jsonArrayWeather = jsonObjectData.getJSONArray("weather");
//            JSONObject jsonObjectWeatherData = jsonArrayWeather.getJSONObject(0);
//            JSONArray jsonArrayHourly = jsonObjectWeatherData.getJSONArray("hourly");
//            JSONObject jsonObjectHourly = jsonArrayHourly.getJSONObject(0);
//
//            double temperature = jsonObjectHourly.getDouble("tempC");
//            double wind = jsonObjectHourly.getDouble("windspeedKmph");
//            double feelsTemperature = jsonObjectHourly.getDouble("FeelsLikeC");
//
//            JSONArray jsonArrayWeatherDesc = jsonObjectHourly.getJSONArray("weatherDesc");
//            JSONObject jsonObjectWeatherDesc = jsonArrayWeatherDesc.getJSONObject(0);
//            String name = jsonObjectWeatherDesc.getString("value");
//
//            JSONArray jsonArrayWeatherIconURL = jsonObjectHourly.getJSONArray("weatherIconUrl");
//            JSONObject jsonObjectWeatherIconURL = jsonArrayWeatherIconURL.getJSONObject(0);
//            String iconURL = jsonObjectWeatherIconURL.getString("value");
//
//            Weather weather = new Weather(name, iconURL, temperature, feelsTemperature, wind);
//
//            return weather;

        //        return null;

    }
}
