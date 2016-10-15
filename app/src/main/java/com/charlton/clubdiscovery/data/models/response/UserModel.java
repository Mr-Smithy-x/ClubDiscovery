package com.charlton.clubdiscovery.data.models.response;

import java.io.Serializable;

/**
 * Created by cj on 10/15/16.
 */

public class UserModel implements Serializable {

    public int user_id;
    public String user_firstname;
    public String user_lastname;
    public String user_email;
    public String user_phone;
    public String user_act_key;
    public String user_creation;
    public String user_permission;
    public String user_profile_photo;
    public String user_splash_photo;
    public SchoolModel user_school_code;
    public MajorModel user_major_id;
    public int user_club_id;

    public int getUser_id() {
        return user_id;
    }

    public int getUser_club_id() {
        return user_club_id;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_act_key() {
        return user_act_key;
    }

    public String getUser_creation() {
        return user_creation;
    }

    public String getUser_permission() {
        return user_permission;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public String getUser_splash_photo() {
        return user_splash_photo;
    }

    public SchoolModel getUser_school_code() {
        return user_school_code;
    }

    public MajorModel getUser_major_id() {
        return user_major_id;
    }

    public String getFullName() {
        return String.format("%s %s", user_firstname, user_lastname);
    }
}
