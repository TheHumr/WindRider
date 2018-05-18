package com.example.thehumr.windrider.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by ondraboura on 17/05/2018.
 */

public class AthleteLoadService extends IntentService {

    public AthleteLoadService() {
        super("AthleteIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fetchAthlete();
    }

    private void fetchAthlete() {

    }
}
