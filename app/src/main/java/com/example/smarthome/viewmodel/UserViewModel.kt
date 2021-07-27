package com.example.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.data.api.UserService
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import com.example.smarthome.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val service: UserService
) : ViewModel() {

    // TODO check if LiveData can be used here instead of MutableLiveData
    val status: LiveData<Resource<User>> = service.status
    val serverStatus: MutableLiveData<Boolean> = service.serverStatus
    val isSignedIn: MutableLiveData<Boolean> = service.isSignedIn

    fun signin(loginRequest: LoginRequest) = service.signin(loginRequest)
    fun signup(user: User) = service.signup(user)
    fun signOut() = service.signOut()
    fun getServerStatus() = service.getServerStatus()

}
