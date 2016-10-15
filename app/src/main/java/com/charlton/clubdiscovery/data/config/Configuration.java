package com.charlton.clubdiscovery.data.config;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cj on 10/14/16.
 */

public class Configuration {

    public final static String BASE_URL = "https://yoprice.co/";

    @StringDef({BASE_URL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CONFIG{}


}
