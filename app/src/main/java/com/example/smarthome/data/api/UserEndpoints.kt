package com.example.smarthome.data.api

import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserEndpoints {

    @POST("/user/signup")
    suspend fun signup(@Body user: User): Response<ResponseBody>

    @POST("/user/signin")
    suspend fun signin(@Body loginRequest: LoginRequest): Response<ResponseBody>

    @GET("/server")
    suspend fun getServerStatus(): ResponseBody
}