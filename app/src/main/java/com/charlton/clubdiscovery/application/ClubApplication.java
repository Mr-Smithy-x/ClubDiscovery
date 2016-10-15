package com.charlton.clubdiscovery.application;

import android.app.Application;

import com.charlton.clubdiscovery.ClubSingleton;

/**
 * Created by cj on 10/14/16.
 */

public class ClubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClubSingleton.initSP(this);
    }

}
