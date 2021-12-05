package com.example.smarthome.data.api

import com.example.smarthome.data.model.BasicResponse
import com.example.smarthome.data.model.Relay
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RelayEndpoints {

    @POST("/relays")
    suspend fun add(@Body relay: Relay): Response<BasicResponse<Relay>>

    @DELETE("/relays/{id}")
    suspend fun deleteById(@Path("id") id: Long): Response<ResponseBody>

    @GET("/relays")
    suspend fun getAll(): Response<List<Relay>>

    @GET("/relays/{id}")
    suspend fun getById(@Path("id") id: Long): Relay

    @PUT("/relays/{id}")
    suspend fun updateById(@Path("id") id: Long, @Body relay: Relay): Response<Relay>
    //fun updateById(@Path("id") id: Long, @Body relay: Relay): Observable<Response<Relay>>

    @POST("/relays/{id}/turn")
    suspend fun turn(@Path("id") id: Long): Response<ResponseBody>

}
