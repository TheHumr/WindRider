package com.example.thehumr.windrider.event;

import com.example.thehumr.windrider.database.table.Segment;
import com.example.thehumr.windrider.enums.State;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class WeatherLoadEvent {

    private State state;
    private Segment segment;

    public WeatherLoadEvent(State state) {
        this.state = state;
    }

    public WeatherLoadEvent(Segment segment) {
        this.segment = segment;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
