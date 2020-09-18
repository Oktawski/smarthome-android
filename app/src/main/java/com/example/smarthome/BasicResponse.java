package com.example.smarthome;


import com.google.gson.annotations.SerializedName;

public class BasicResponse<T> {

    @SerializedName("object")
    protected T t;

    protected String msg;

    public BasicResponse() {
    }

    public BasicResponse(T t, String msg) {
        this.t = t;
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
