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
    fun deleteById(@Path("id") id: Long): Observable<Response<ResponseBody>>

    @GET("/relays")
    fun getAll(): Single<Response<List<Relay>>>

    @GET("/relays/{id}")
    fun getById(@Path("id") id: Long): Single<Relay>

    @PUT("/relays/{id}")
    fun updateById(@Path("id") id: Long, @Body relay: Relay): Observable<Response<Relay>>

    @POST("/relays/{id}/turn")
    fun turn(@Path("id") id: Long): Single<Response<ResponseBody>>

}
