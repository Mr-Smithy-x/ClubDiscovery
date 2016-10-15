package com.charlton.clubdiscovery.data.models.response;

import java.io.Serializable;

/**
 * Created by cj on 10/15/16.
 */
public class MajorModel implements Serializable {
    public int major_id;
    public String major_title;
    public String major_category;
    public String major_file_link;

    public int getMajor_id() {
        return major_id;
    }

    public String getMajor_title() {
        return major_title;
    }

    public String getMajor_category() {
        return major_category;
    }

    public String getMajor_file_link() {
        return major_file_link;
    }
}
