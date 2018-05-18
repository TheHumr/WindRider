package com.example.thehumr.windrider.event;

import com.example.thehumr.windrider.enums.State;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class SegmentLoadEvent {

    private State state;

    public SegmentLoadEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
