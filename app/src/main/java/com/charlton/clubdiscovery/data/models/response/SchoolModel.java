package com.charlton.clubdiscovery.data.models.response;

import java.io.Serializable;

/**
 * Created by cj on 10/15/16.
 */
public class SchoolModel implements Serializable{
    public int school_id;
    public int school_code;
    public String school_name;
    public float school_lat;
    public float school_lng;
    public String school_address;

    public String getSchool_address() {
        return school_address;
    }

    public float getSchool_lng() {
        return school_lng;
    }

    public float getSchool_lat() {
        return school_lat;
    }

    public String getSchool_name() {
        return school_name;
    }

    public int getSchool_code() {
        return school_code;
    }

    public int getSchool_id() {
        return school_id;
    }
}
