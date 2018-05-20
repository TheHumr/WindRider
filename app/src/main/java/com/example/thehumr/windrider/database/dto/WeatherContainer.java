package com.example.thehumr.windrider.database.dto;


import com.example.thehumr.windrider.database.table.Weather;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class WeatherContainer {

    List<WeatherInTime> list;

    String dataJSON;

    public Weather toWeather() {
        Weather weather = new Weather();
        weather.setSyncDateLong(new Date().getTime());
        weather.setData(getWeathersDataJSON());
        return weather;
    }

    public String getWeathersDataJSON() {
        if (dataJSON == null) {
            dataJSON = new Gson().toJson(list);
        }
        return dataJSON;
    }

}
