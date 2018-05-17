package com.example.thehumr.windrider.activity;

import android.content.Intent;

import com.samsandberg.stravaauthenticator.StravaAuthenticateActivity;
import com.samsandberg.stravaauthenticator.StravaScopes;

import java.util.Arrays;
import java.util.Collection;


public class LoginActivity extends StravaAuthenticateActivity {

    /*****************************************
     * Methods override START
     */

    /**
     * Client ID
     */
    protected String getStravaClientId() {
        return "22275";
    }

    /**
     * Client Secret
     */
    protected String getStravaClientSecret() {
        return "f5440dabc89a6777097b501f7cd38d691cf8cacc";
    }

    /**
     * Scopes to auth for
     * (default public)
     */
    protected Collection<String> getStravaScopes() {
        return Arrays.asList(StravaScopes.SCOPE_PUBLIC);
    }

    /**
     * Should we use the local cache?
     * (default true)
     */
    protected boolean getStravaUseCache() {
        return true;
    }

    /**
     * Should we check a token (against Strava's API) or should we just assume it's good?
     * (default true)
     */
    protected boolean getStravaCheckToken() {
        return true;
    }

    /**
     * What intent should we kick off, given OK auth
     */
    protected Intent getStravaActivityIntent() {
        return new Intent(this, MainActivity.class);
    }

    /**
     * Should we finish this activity after successful auth + kicking off next activity?
     * (default true)
     */
    protected boolean getStravaFinishOnComplete() {
        return true;
    }

    /**
     * Methods override END
     ****************************************/
}