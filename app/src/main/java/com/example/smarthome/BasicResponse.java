package com.example.smarthome;


import com.google.gson.annotations.SerializedName;

public class BasicResponse<T extends Object> {

    @SerializedName("object")
    protected T t;

    @SerializedName("msg")
    protected String msg;

    public BasicResponse() {
    }

    public BasicResponse(T t, String msg) {
        this.t = t;
        this.msg = msg;
    }

    public T getObject() {
        return t;
    }

    public void setObject(T t) {
        this.t = t;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
