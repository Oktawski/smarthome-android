package com.example.smarthome.data.api

import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Light
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LightEndpoints {

    @POST("/lights")
    suspend fun add(@Body light: Light): Response<BasicResponse<Light>>

    @DELETE("/lights/{id}")
    suspend fun deleteById(@Path("id") id: Long): Response<ResponseBody>

    @GET("/lights")
    suspend fun getAll(): Response<List<Light>>

    @GET("/lights/{id}")
    suspend fun getById(@Path("id") id: Long) : Light

    @PUT("/lights/{id}")
    suspend fun updateById(@Path("id") id: Long, @Body light: Light): Response<Light>

    @POST("/lights/{id}/turn")
    suspend fun turn(@Path("id") id: Long): Response<Light>

    @POST("/lights/{id}/color")
    suspend fun setColor(@Path("id") id: Long, @Body light: Light): Response<ResponseBody>

}