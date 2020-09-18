package com.example.smarthome.relays.services;

import com.example.smarthome.WifiDevice;
import com.example.smarthome.relays.models.Relay;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRelayRetrofitService {

    @POST("/relays")
    Call<JsonObject> addRelay(@Body Relay relay);

    @DELETE("/relays/{id}")
    Call<JsonObject> deleteRelayById(@Path("id") Long id);

    @GET("/relays")
    Call<List<Relay>> getRelays();

    @GET("/relays/{id}")
    Call<Relay> getRelayById(@Path("id") Long id);

    @GET("/relays/ip/{ip}")
    Call<JsonObject> getRelayByIp(@Path("ip") String ip);

    @PUT("/relays/{id}")
    Call<JsonObject> updateRelayById(@Path("id") Long id, Relay relay);

    @POST("/relays/{id}/turn")
    Call<ResponseBody> turnRelay(@Path("id") Long id);

}
