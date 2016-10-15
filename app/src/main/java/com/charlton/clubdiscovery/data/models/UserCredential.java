package com.charlton.clubdiscovery.data.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/**
 * Created by cj on 10/14/16.
 */
public class UserCredential extends HashMap<String, String> {
    public static final String EMAIL = "email", PASS = "pass", PHONE = "phone", FIRST_NAME = "first_name", LAST_NAME = "last_name";

    @StringDef({EMAIL, PASS, PHONE, FIRST_NAME, LAST_NAME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Credentials{}

    private UserCredential(){}

    public static UserCredential Factory(){
        return new UserCredential();
    }

    public UserCredential addParam(@Credentials String key, String value){
        put(key,value);
        return this;
    }

    public UserCredential build(){
        return this;
    }

    public static UserCredential Login(String email, String pass){
        return Factory().
                addParam(EMAIL, email).
                addParam(PASS,pass);
    }

    public static UserCredential Register(String email, String pass, String phone, String first_name, String last_name){
        return Factory().
                addParam(EMAIL, email).
                addParam(PASS,pass).
                addParam(PHONE,phone).
                addParam(FIRST_NAME, first_name).
                addParam(LAST_NAME, last_name);
    }

}
