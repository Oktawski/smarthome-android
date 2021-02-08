package com.example.smarthome.ligths.services;

import com.example.smarthome.RetrofitContext;

public class LightService {

    private final static ILightRetrofitService service = RetrofitContext.getLightService();

    private static LightService instance;

    public static LightService getInstance() {
        if(instance == null){
            instance = new LightService();
        }
        return instance;
    }

}
