package com.charlton.clubdiscovery;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;

import com.charlton.clubdiscovery.data.config.Configuration;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.charlton.clubdiscovery.data.service.ClubService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cj on 10/14/16.
 */
public class ClubSingleton {
    public static final String SP_USER_KEY = "USER";
    public static final String SP_CLUB_KEY = "CLUB";


    public static boolean hasClub() {
        return getSharedPreferences().contains(SP_CLUB_KEY);
    }


    public static ClubModel getClub() {
        String json = getSharedPreferences().getString(SP_CLUB_KEY, null);
        return new GsonBuilder().setLenient().create().fromJson(json, ClubModel.class);
    }

    public static void setClub(ClubModel club) {
        String json = new GsonBuilder().setLenient().create().toJson(club);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(SP_CLUB_KEY,json);
        editor.apply();
    }

    public boolean clearClub(){
        if(hasClub()) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.remove(SP_CLUB_KEY);
            editor.apply();
        }
        return true;
    }

    @StringDef({SP_USER_KEY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SPKEY {
    }

    private SharedPreferences sharedPreferences;
    private static final String SHARED_KEY = "ClubDiscovery";
    private static ClubSingleton ourInstance = new ClubSingleton();

    public static ClubSingleton getInstance() {
        return ourInstance;
    }

    private ClubService clubService = null;

    public static ClubService getClubService() {
        return getInstance().clubService;
    }

    public static SharedPreferences getSharedPreferences() {
        return getInstance().sharedPreferences;
    }

    public static boolean hasUser() {
        return getSharedPreferences().contains(SP_USER_KEY);
    }


    public static UserModel getUser() {
        String json = getSharedPreferences().getString(SP_USER_KEY, null);
        return new GsonBuilder().setLenient().create().fromJson(json, UserModel.class);
    }

    public static void setUser(UserModel user) {
        String json = new GsonBuilder().setLenient().create().toJson(user);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(SP_USER_KEY,json);
        editor.apply();
    }

    public static boolean logOut(){
        if(hasUser()) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.remove(SP_USER_KEY);
            editor.apply();
        }
        return true;
    }

    public static void initSP(Context context) {
        getInstance().sharedPreferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE);
    }

    private ClubSingleton() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Configuration.BASE_URL).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).
                build();
        clubService = retrofit.create(ClubService.class);
    }
}
