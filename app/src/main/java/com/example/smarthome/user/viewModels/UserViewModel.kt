package com.example.smarthome.user.viewModels

import androidx.lifecycle.ViewModel
import com.example.smarthome.user.models.LoginBody
import com.example.smarthome.user.models.User
import com.example.smarthome.user.services.UserService

class UserViewModel : ViewModel(){

    private val service = UserService.getInstance()

    fun getSignupMsg() = service.signupMsg
    fun getLoginMsg() =  service.loginMsg
    fun getIsSignedIn() = service.signedIn
    fun showProgressBar() = service.showProgressBar


    fun signin(loginBody: LoginBody){
        service.signin(loginBody)
    }

    fun signup(user: User){
        service.signup(user)
    }
}