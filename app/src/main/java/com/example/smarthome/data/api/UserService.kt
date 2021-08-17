package com.example.smarthome.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smarthome.data.model.JwtToken
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import com.example.smarthome.utilities.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class UserService @Inject constructor(
    private val api: UserEndpoints
) {

    private val _status = MutableLiveData<Resource<User>>()
            val status: LiveData<Resource<User>> get() = _status
    val serverStatus = MutableLiveData(false)
    val isSignedIn = User.isSignedIn

    var job: Job? = null
    private val errorMessage = "Error connecting to server"

    fun signin(loginRequest: LoginRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.signin(loginRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    JwtToken.jwtToken = response.headers()["Authorization"]!!
                    isSignedIn.value = true
                    _status.value = Resource.success("Welcome ${loginRequest.username}")
                }
                else {
                    signOut()
                    _status.value = Resource.error("Wrong credentials")
                }
            }
        }
    }

    fun signup(user: User) {
        _status.value = Resource.loading()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = api.signup(user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Resource.success(user, "Account created")
                }
                else {
                    _status.value = Resource.error("Something went wrong")
                }
                clearStatus()
            }
        }
    }

    fun signOut(){
        isSignedIn.value = false
        JwtToken.clear()
        clearStatus()
    }

    suspend fun getServerStatus() = api.getServerStatus()

    private fun clearStatus(){
        _status.value = Resource.none()
    }

}