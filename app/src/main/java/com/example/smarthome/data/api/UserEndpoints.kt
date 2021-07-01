package com.example.smarthome.data.api

import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserEndpoints {
    @POST("/user/signup")
    fun signup(@Body user: User): Observable<Response<ResponseBody>>

    @POST("/user/signin")
    fun signin(@Body loginRequest: LoginRequest): Observable<Response<ResponseBody>>

    @GET("/server")
    fun getStatus(): Call<ResponseBody>
}