package com.example.thehumr.windrider.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.dto.WeatherContainer;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.enums.State;
import com.example.thehumr.windrider.event.SegmentLoadEvent;
import com.example.thehumr.windrider.network.RestClient;
import com.example.thehumr.windrider.network.WeatherService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class WeatherLoadService extends IntentService {

    public static State state = State.LOAD_SUCCESS;

    public WeatherLoadService() {
        super("WeatherLoadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EventBus.getDefault().post(new SegmentLoadEvent(State.LOADING));
        for (Segment segment : SegmentDAO.getAllStarredSegment()) {
            fetchWeather(segment);
        }
        EventBus.getDefault().post(new SegmentLoadEvent(state));

    }

    private void fetchWeather(Segment segment) {
        Call<WeatherContainer> weatherCall = RestClient.getWeatherService().getWeatherByCoordinates(WeatherService.API_KEY, segment.getLatitudeStart(), segment.getLongitudeStart());

        weatherCall.enqueue(new Callback<WeatherContainer>() {
            @Override
            public void onResponse(Call<WeatherContainer> call, Response<WeatherContainer> response) {
                WeatherContainer weatherContainer = response.body();
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
