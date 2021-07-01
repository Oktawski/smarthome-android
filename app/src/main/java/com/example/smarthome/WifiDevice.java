package com.example.smarthome;

import com.example.smarthome.utilities.ViewType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WifiDevice implements ViewType {

    @SerializedName("name")
    @Expose
    String name = "No name";

    @SerializedName("ip")
    @Expose
    String ip;

    @SerializedName("on")
    @Expose
    Boolean on = false;

    public WifiDevice(){}

    public WifiDevice(String name, String ip, Boolean on){
        this.name = !name.equals("") ? name : "NoName";
        this.ip = !ip.equals("") ? ip : "No ip";
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean getOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
