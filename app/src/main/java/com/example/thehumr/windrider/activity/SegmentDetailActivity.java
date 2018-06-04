package com.example.thehumr.windrider.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.thehumr.windrider.R;
import com.example.thehumr.windrider.database.dao.SegmentDAO;
import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.fragment.SegmentsFragment;
import com.example.thehumr.windrider.utils.EvaluationUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

/**
 * Created by ondraboura on 20/05/2018.
 */

public class SegmentDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    Segment segment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.segment);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int segmentId = getIntent().getIntExtra(SegmentsFragment.ARG_ITEM_ID, 0);
        if (segmentId != 0) {
            segment = SegmentDAO.getSegment(segmentId);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        for (int i = 0; i < segment.getMap().getCoordinates().size() - 1; i++) {
            if (i == 0) {
                addStartMarker(googleMap, segment.getMap().getCoordinates().get(0));
            }
            addEvaluatedPolyline(googleMap, segment.getMap().getCoordinates().get(i), segment.getMap().getCoordinates().get(i + 1), segment.getWeather().getWeathers().get(0).getWind().getDegree());
        }

        double[] bounds = segment.getMap().getBorders();
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(bounds[3], bounds[0]), new LatLng(bounds[1], bounds[2]));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(segment.getMap().getCentreCoordinate(), 12));
        googleMap.setLatLngBoundsForCameraTarget(latLngBounds);

//        googleMap.setOnPolylineClickListener(this);
//        googleMap.setOnPolygonClickListener(this);
    }

    private void addStartMarker(GoogleMap googleMap, LatLng latLng) {
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
        );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void addEvaluatedPolyline(GoogleMap googleMap, LatLng start, LatLng end, double windDegree) {
        double heading = SphericalUtil.computeHeading(start, end);
        googleMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .color(EvaluationUtils.getEvaluatedColor(this, heading, windDegree))
                .add(start, end));
    }
}
