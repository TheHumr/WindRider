package com.example.thehumr.windrider.database.table;

import com.example.thehumr.windrider.database.MyDatabase;
import com.example.thehumr.windrider.database.dto.WeatherInTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ondraboura on 19/05/2018.
 */

@Table(database = MyDatabase.class)
public class Weather extends BaseModel {

    @Column
    @PrimaryKey
    int id;
    @Column
    long syncDateLong;
    @Column
    String dataJSON;

    List<WeatherInTime> weathers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSyncDateLong() {
        return syncDateLong;
    }

    public void setSyncDateLong(long syncDateLong) {
        this.syncDateLong = syncDateLong;
    }

    public String getData() {
        return dataJSON;
    }

    public void setData(String dataJSON) {
        this.dataJSON = dataJSON;
    }

    public List<WeatherInTime> getWeathers() {
        if (weathers == null) {
            Type listType = new TypeToken<ArrayList<WeatherInTime>>(){}.getType();
            weathers = new Gson().fromJson(dataJSON, listType);
        }
        return weathers;
    }

    public void setWeathers(List<WeatherInTime> weathers) {
        this.weathers = weathers;
    }
}
