package com.example.smarthome.user.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.user.models.LoginRequest
import com.example.smarthome.user.models.User
import com.example.smarthome.user.services.UserService

class UserViewModel : ViewModel(){

    private val service = UserService.getInstance()

    fun getSignupMsg(): LiveData<String> = service.signupMsg
    fun getLoginMsg(): LiveData<String> =  service.loginMsg
    fun getIsSignedIn(): LiveData<Boolean> = service.signedIn
    fun showProgressBar(): LiveData<Boolean> = service.showProgressBar
    fun getServerStatusLD(): LiveData<Boolean> = service.serverStatusLD


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