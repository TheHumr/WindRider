package com.example.thehumr.windrider.database.table;

import com.example.thehumr.windrider.database.MyDatabase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ondraboura on 18/05/2018.
 */

@Table(database = MyDatabase.class)
public class Map extends BaseModel {

    @Column
    @PrimaryKey
    String id;
    @Column
    String polyline;
    @Column
    @SerializedName("resource_state")
    int resourceState;

    List<LatLng> coordinates;

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

    public List<LatLng> getCoordinates() {
        if (coordinates == null) {
            coordinates = PolyUtil.decode(polyline);
        }
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }

    public double getAngle() {
        return SphericalUtil.computeHeading(getCoordinates().get(0), getCoordinates().get(getCoordinates().size() - 1));
    }

    public double getAngle(LatLng start, LatLng end) {
        return SphericalUtil.computeHeading(start, end);
    }

    public List<Double> getAngles() {
        List<Double> angles = new ArrayList<>();
        for (int i = 0; i < getCoordinates().size() - 1; i++) {
            angles.add(getAngle(getCoordinates().get(i), getCoordinates().get(i + 1)));
        }
        return angles;
    }

    public double[] getBorders() {
        double left = 0;
        double top = 0;
        double right = 0;
        double bottom = 0;
        for (LatLng latLng : getCoordinates()) {
            if (latLng.latitude > top || top == 0) {
                top = latLng.latitude;
            }
            if (latLng.latitude < bottom || bottom == 0) {
                bottom = latLng.latitude;
            }
            if (latLng.longitude > right || right == 0) {
                right = latLng.longitude;
            }
            if (latLng.longitude < left || left == 0) {
                left = latLng.longitude;
            }
        }
        return new double[]{left, top, right, bottom};
    }

    public LatLng getCentreCoordinate() {
        double[] borders = getBorders();
        return new LatLng((borders[1] + borders[3]) / 2, (borders[0] + borders[2]) / 2);
    }
}
