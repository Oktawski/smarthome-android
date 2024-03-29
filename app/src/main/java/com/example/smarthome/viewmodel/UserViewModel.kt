package com.example.smarthome.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.UserService
import com.example.smarthome.data.model.JwtToken
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val service: UserService
) : ViewModel() {

    val status: MutableLiveData<Resource<User>> = MutableLiveData()
    val serverStatus: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedIn: MutableLiveData<Boolean> = MutableLiveData()

    private var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun signin(loginRequest: LoginRequest) {
        status.value = Resource.loading()

        job = Job(viewModelScope.launch {
            try  {
                val response = service.signin(loginRequest)
                handleLoginResponse(loginRequest.username, response)
            } catch (exception: ConnectException) {
                this.cancel()
                handleConnectionError(exception)
            }
        })
    }

    fun signup(user: User) {
        job = Job(viewModelScope.launch {
            try {
                val response = service.signup(user)
                handleRegisterResponse(response)
            } catch (exception: ConnectException) {
                this.cancel()
                handleConnectionError(exception)
            }
        })
    }

    fun signOut() {
        JwtToken.clear()
        isSignedIn.value = false
        status.value = Resource.none()
    }

    suspend fun getServerStatus() = service.getServerStatus()

    private fun handleLoginResponse(username: String, response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            isSignedIn.value = response.isSuccessful
            JwtToken.jwtToken = response.headers()["Authorization"]!!
            status.value = Resource.success("Welcome $username :)")
        } else {
            status.value = Resource.error("Wrong credentials mate")
            signOut()
        }
    }

    private fun handleRegisterResponse(response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            status.value = Resource.success("Account created, you can sign in now :)")
        } else {
            status.value = Resource.error("Something went wrong. Try again later")
        }
    }

    private fun handleConnectionError(exception: ConnectException) {
        exception.printStackTrace()
        status.value = Resource.error("Could not connect to server")
    }

}
