package com.example.smarthome.ligths.services

import com.example.smarthome.ligths.models.Light
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

//TODO update CALL return type
interface ILightRetrofitService {

    @GET("/lights")
    fun getAll(): Call<List<Light>>

    @GET("/lights/{id}")
    fun getById(@Path("id") id: Long) : Call<Light>

    @GET("/lights/ip/{ip}")
    fun getByIp(@Path("ip") ip: String) : Call<Light>

    @POST("/lights")
    fun add(@Body light: Light) : Call<JsonObject>

    @POST("/lights/{id}/turn")
    fun turn(@Path("id") id: Long) : Call<ResponseBody>

    @PUT("/lights/{id}")
    fun updateById(@Path("id") id: Long, @Body light: Light) : Call<ResponseBody>

    @DELETE("/lights/{id}")
    fun deleteById(@Path("id") id: Long) : Call<ResponseBody>

}