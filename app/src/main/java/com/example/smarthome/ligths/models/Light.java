package com.example.smarthome.ligths.models;

import android.os.strictmode.CredentialProtectedWhileLockedViolation;

import com.example.smarthome.WifiDevice;
import com.google.gson.annotations.SerializedName;

public class Light extends WifiDevice {

    private Long id;
    private short red;
    private short green;
    private short blue;
    private short intensity;

    public Light(){}

    public Light(String name, String ip, Boolean on, short red, short green, short blue, short intensity){
        super(name, ip, on);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.intensity = intensity;
    }

    public Long getId() {
        return id;
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    public short getIntensity() {
        return intensity;
    }
}
