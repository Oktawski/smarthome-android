package com.example.smarthome.user.services

import com.example.smarthome.user.models.LoginBody
import com.example.smarthome.user.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUserRetrofitService {
    @POST("/user/signup")
    fun signup(@Body user: User) : Call<String>

    @POST("/user/signin")
    fun signin(@Body loginBody: LoginBody) : Call<ResponseBody>
}