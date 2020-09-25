package com.example.smarthome.ligths.services;

import com.example.smarthome.RetrofitContext;
import com.example.smarthome.ligths.models.Light;
import com.example.smarthome.relays.services.RelayService;

public class LightService {

    private final static ILightRetrofitService service = RetrofitContext.getLightService();

    private static LightService instance;

    public static LightService getInstance() {
        if(instance == null){
            instance = new LightService();
        }
        return instance;
    }

    public void addLight(Light light){

    }
}
