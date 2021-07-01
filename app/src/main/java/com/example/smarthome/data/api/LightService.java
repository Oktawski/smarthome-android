package com.example.smarthome.data.api;

import com.example.smarthome.RetrofitContext;

public class LightService {

    private final static LightEndpoints service = new RetrofitContext().getInstance(LightEndpoints.class);

    private static LightService instance;

    public static LightService getInstance() {
        if(instance == null){
            instance = new LightService();
        }
        return instance;
    }

}
