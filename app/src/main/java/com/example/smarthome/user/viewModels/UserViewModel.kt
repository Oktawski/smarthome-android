package com.example.smarthome.user.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.user.models.LoginRequest
import com.example.smarthome.user.models.User
import com.example.smarthome.user.services.UserService
import com.example.smarthome.utilities.Resource

class UserViewModel : ViewModel(){

    private val service = UserService.getInstance()

    fun getIsSignedIn(): LiveData<Boolean> = service.signedIn
    fun getServerStatusLD(): LiveData<Boolean> = service.serverStatusLD
    fun getStatus(): LiveData<Resource<User>> = service.status


    fun signin(loginRequest: LoginRequest){
        service.signin(loginRequest)
    }

    fun signup(user: User){
        service.signup(user)
    }

    fun signOut(){
        service.signOut()
    }

    fun getServerStatus(){
        service.getServerStatus()
    }
}