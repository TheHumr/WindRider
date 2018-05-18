package com.example.thehumr.windrider.database.dto;

import com.example.thehumr.windrider.database.table.Segment;

import java.util.List;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class SegmentContainer {

    private List<Segment> segments;

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
