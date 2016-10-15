package com.charlton.clubdiscovery.data.models;

/**
 * Created by cj on 10/15/16.
 */

public class ResponseModel<T> {
    public int status;
    public String message;
    public String explain;
    public T data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getExplain() {
        return explain;
    }

    public T getData() {
        return data;
    }
}
