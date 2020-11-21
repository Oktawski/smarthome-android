package com.example.smarthome.ligths.services;

import com.example.smarthome.ligths.models.Light;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ILightRetrofitService {

    @POST("/lights")
    Call<JsonObject> addLight();

    @DELETE("/lights/{id}")
    Call<JsonObject> deleteLightById(@Path("id") Long id);

    @GET("/lights")
    Call<JsonObject> getLights();

    @GET("/lights/{id}")
    Call<JsonObject> getLightById(@Path("id") Long id);

    @GET("/lights/ip/{ip}")
    Call<JsonObject> getLightByIp(@Path("ip") String ip);

    @PUT("lights/{id}")
    Call<JsonObject> updateLightById(@Path("id") Long id, Light light);

}
