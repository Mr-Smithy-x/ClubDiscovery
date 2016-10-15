package com.charlton.clubdiscovery.data.models.response;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by cj on 10/15/16.
 */

public class ClubModel implements Serializable{

    public int club_id;
    public String club_title;
    public String club_description;
    public UserModel club_president_id;
    public UserModel club_officer_1;
    public UserModel club_officer_2;
    public UserModel club_officer_3;
    public String club_general_email;
    public String club_start;
    public String club_end;
    public SchoolModel club_school_code;

    public SchoolModel getClub_school_code() {
        return club_school_code;
    }

    public String getTime(String your_Time){
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
            java.util.Date d1 =(java.util.Date)format.parse(your_Time);
            java.sql.Time ppstime = new java.sql.Time(d1.getTime());
            return ppstime.toString();
        } catch(Exception e) {
            Log.e("Exception is ", e.toString());
        }
        return "?";
    }

    public int getClub_id() {
        return club_id;
    }

    public String getClub_title() {
        return club_title;
    }

    public String getClub_description() {
        return club_description;
    }

    public UserModel getClub_president() {
        return club_president_id;
    }

    public UserModel getClub_officer_1() {
        return club_officer_1;
    }

    public UserModel getClub_officer_2() {
        return club_officer_2;
    }

    public UserModel getClub_officer_3() {
        return club_officer_3;
    }

    public String getClub_general_email() {
        return club_general_email;
    }

    public String getClub_start() {
        return club_start;
    }

    public String getClub_end() {
        return club_end;
    }
}
