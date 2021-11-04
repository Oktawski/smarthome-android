package com.example.smarthome.data.model;

import com.example.smarthome.utilities.ViewType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WifiDevice implements ViewType {

    @SerializedName("name")
    @Expose
    String name = "No name";

    @SerializedName("mac")
    @Expose
    String mac;

    @SerializedName("on")
    @Expose
    Boolean on = false;

    public WifiDevice(){}

    public WifiDevice(String name, String mac, Boolean on){
        this.name = !name.equals("") ? name : "NoName";
        this.mac = !mac.equals("") ? mac : "No MAC";
        this.on = on;
    }

    @Override
    public int getViewType() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean getOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
