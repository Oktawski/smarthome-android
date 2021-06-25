package com.example.smarthome.relays.services;

import com.example.smarthome.BasicResponse;
import com.example.smarthome.relays.models.Relay;
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

public interface RelayEndpoints {

    @POST("/relays")
    Call<BasicResponse<Relay>> add(@Body Relay relay);

    @DELETE("/relays/{id}")
    Call<ResponseBody> deleteById(@Path("id") Long id);

    @GET("/relays")
    Call<List<Relay>> getAll();

    @GET("/relays/{id}")
    Call<Relay> getById(@Path("id") Long id);

    @GET("/relays/ip/{ip}")
    Call<BasicResponse<Relay>> getByIp(@Path("ip") String ip);

    @PUT("/relays/{id}")
    Call<Relay> updateById(@Path("id") Long id, @Body Relay relay);

    @POST("/relays/{id}/turn")
    Call<ResponseBody> turn(@Path("id") Long id);
}
