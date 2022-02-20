package com.example.smarthome.data.api

import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import com.example.smarthome.utilities.Resource
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

class UserService @Inject constructor(
    private val api: UserEndpoints
) {

    suspend fun signin(loginRequest: LoginRequest): Response<ResponseBody> {
        return api.signin(loginRequest)
    }

    suspend fun signup(user: User): Response<ResponseBody> {
        return api.signup(user)
    }

    suspend fun getServerStatus() = api.getServerStatus()
}