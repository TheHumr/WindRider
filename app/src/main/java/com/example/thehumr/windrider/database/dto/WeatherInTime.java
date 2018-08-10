package com.example.thehumr.windrider.database.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class WeatherInTime {

    @SerializedName("dt")
    long dateLong;
    @SerializedName("wind")
    Wind wind;

    public class Wind {

        @SerializedName("speed")
        double speed;
        @SerializedName("deg")
        double degree;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDegree() {
            return degree;
        }

        public void setDegree(double degree) {
            this.degree = degree;
        }
    }

    public long getDateLong() {
        return dateLong;
    }

    public Wind getWind() {
        return wind;
    }
}
