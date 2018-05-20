package com.example.thehumr.windrider.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.thehumr.windrider.app.MyApplication;
import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.dto.SegmentContainer;
import com.example.thehumr.windrider.database.dto.WeatherContainer;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.database.table.Weather;
import com.example.thehumr.windrider.enums.State;
import com.example.thehumr.windrider.event.SegmentLoadEvent;
import com.example.thehumr.windrider.network.RestClient;
import com.example.thehumr.windrider.network.WeatherService;
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
                        fetchSegment(segment);
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

    private void fetchSegment(final Segment segment) {
        Call<Segment> segmentCall = RestClient.getStravaService().getSegment(segment.getId(), StravaAuthenticateActivity.getStravaAccessToken(getApplicationContext()));

        segmentCall.enqueue(new Callback<Segment>() {
            @Override
            public void onResponse(Call<Segment> call, Response<Segment> response) {
                if (response.body() != null) {
                    Segment downloadedSegment = response.body();
                    SegmentDAO.saveWithReferences(downloadedSegment);
                    fetchWeather(downloadedSegment);
                    state = State.LOAD_SUCCESS;
                } else {
                    state = State.LOAD_SUCCESS_EMPTY;
                }
            }

            @Override
            public void onFailure(Call<Segment> call, Throwable t) {
                state = State.LOAD_FAILURE;
            }
        });
    }

    private void fetchWeather(final Segment segment) {
        Call<WeatherContainer> weatherCall = RestClient.getWeatherService().getWeatherByCoordinates(WeatherService.API_KEY, segment.getLatitudeStart(), segment.getLongitudeStart());

        weatherCall.enqueue(new Callback<WeatherContainer>() {
            @Override
            public void onResponse(Call<WeatherContainer> call, Response<WeatherContainer> response) {
                WeatherContainer weatherContainer = response.body();
                Weather newWeather = weatherContainer.toWeather();
                if (segment.getWeather() != null) {
                    segment.getWeather().delete();
                }
                segment.setWeather(newWeather);
                SegmentDAO.saveWithReferences(segment);
                if (weatherContainer != null) {
                    state = State.LOAD_SUCCESS;
                } else {
                    state = State.LOAD_SUCCESS_EMPTY;
                }
            }

            @Override
            public void onFailure(Call<WeatherContainer> call, Throwable t) {
                state = State.LOAD_FAILURE;
            }
        });

    }

}
