package com.example.thehumr.windrider.database.table;

import com.example.thehumr.windrider.database.MyDatabase;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.PolyUtil;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ondraboura on 17/05/2018.
 */

@Table(database = MyDatabase.class)
public class Segment extends BaseModel {

    @Column
    @PrimaryKey
    int id;
    @Column
    String name;
    @Column
    double distance;
    @Column
    @SerializedName("average_grade")
    double averageGrade;
    @Column
    @SerializedName("maximum_grade")
    double maxGrade;
    @Column
    @SerializedName("start_latitude")
    double latitudeStart;
    @Column
    @SerializedName("end_latitude")
    double latitudeEnd;
    @Column
    @SerializedName("start_longitude")
    double longitudeStart;
    @Column
    @SerializedName("end_longitude")
    double longitudeEnd;
    @Column
    @SerializedName("climb_category")
    int climbCategory;
    @Column
    String city;
    @Column
    String state;
    @Column
    String country;
    @Column
    @SerializedName("total_elevation_gain")
    double totalElevationGain;

    @ForeignKey
    @SerializedName("map")
    Map map;
    @SerializedName("athlete_segment_stats")
    AthleteSegmentStats athleteSegmentStats;
    @Column
    int prElapsedTime;
    @Column
    String prDate;
    @Column
    int effortCount;

    @ForeignKey
    Weather weather;


    public Segment() {
    }

    public String getLocation() {
        return String.format("%s, %s", city, country);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public double getLatitudeStart() {
        return latitudeStart;
    }

    public void setLatitudeStart(double latitudeStart) {
        this.latitudeStart = latitudeStart;
    }

    public double getLatitudeEnd() {
        return latitudeEnd;
    }

    public void setLatitudeEnd(double latitudeEnd) {
        this.latitudeEnd = latitudeEnd;
    }

    public double getLongitudeStart() {
        return longitudeStart;
    }

    public void setLongitudeStart(double longitudeStart) {
        this.longitudeStart = longitudeStart;
    }

    public double getLongitudeEnd() {
        return longitudeEnd;
    }

    public void setLongitudeEnd(double longitudeEnd) {
        this.longitudeEnd = longitudeEnd;
    }

    public int getClimbCategory() {
        return climbCategory;
    }

    public void setClimbCategory(int climbCategory) {
        this.climbCategory = climbCategory;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTotalElevationGain() {
        return totalElevationGain;
    }

    public void setTotalElevationGain(double totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    public Map getMap() {
        if (map == null) {

        }
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public AthleteSegmentStats getAthleteSegmentStats() {
        return athleteSegmentStats;
    }

    public void setAthleteSegmentStats(AthleteSegmentStats athleteSegmentStats) {
        this.athleteSegmentStats = athleteSegmentStats;
    }

    public int getPrElapsedTime() {
        return prElapsedTime;
    }

    public void setPrElapsedTime(int prElapsedTime) {
        this.prElapsedTime = prElapsedTime;
    }

    public String getPrDate() {
        return prDate;
    }

    public void setPrDate(String prDate) {
        this.prDate = prDate;
    }

    public int getEffortCount() {
        return effortCount;
    }

    public void setEffortCount(int effortCount) {
        this.effortCount = effortCount;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public class AthleteSegmentStats {

        @SerializedName("pr_elapsed_time")
        int prElapsedTime;
        @SerializedName("pr_date")
        String prDate;
        @SerializedName("effort_count")
        int effortCount;

        public int getPrElapsedTime() {
            return prElapsedTime;
        }

        public String getPrDate() {
            return prDate;
        }

        public int getEffortCount() {
            return effortCount;
        }
    }

    @Override
    public boolean save() {
        if (athleteSegmentStats != null) {
            this.prElapsedTime = athleteSegmentStats.getPrElapsedTime();
            this.prDate = athleteSegmentStats.getPrDate();
            this.effortCount = athleteSegmentStats.getEffortCount();
        }
        return super.save();
    }
}
