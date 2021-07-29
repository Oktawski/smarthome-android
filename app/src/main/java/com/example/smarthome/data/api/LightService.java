package com.example.smarthome.data.api;


public class LightService {


    private static LightService instance;

    public static LightService getInstance() {
        if(instance == null){
            instance = new LightService();
        }
        return instance;
    }

}
