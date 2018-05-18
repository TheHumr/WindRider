package com.example.thehumr.windrider.database.table;

import com.example.thehumr.windrider.database.MyDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by ondraboura on 18/05/2018.
 */

@Table(database = MyDatabase.class)
public class Map {

    @Column
    @PrimaryKey
    String id;
    @Column
    String polyline;
    @Column
    @SerializedName("resource_state")
    int resourceState;

    public Map() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public int getResourceState() {
        return resourceState;
    }

    public void setResourceState(int resourceState) {
        this.resourceState = resourceState;
    }
}
