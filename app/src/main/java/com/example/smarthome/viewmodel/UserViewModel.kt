package com.example.smarthome.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smarthome.RetrofitContext
import com.example.smarthome.data.api.UserEndpoints
import com.example.smarthome.data.model.JwtToken
import com.example.smarthome.data.model.LoginRequest
import com.example.smarthome.data.model.User
import com.example.smarthome.utilities.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel(){

    private val api: UserEndpoints = RetrofitContext().getInstance(UserEndpoints::class.java)
    val serverStatus: MutableLiveData<Boolean> = MutableLiveData()
    val status: MutableLiveData<Resource<User>> = MutableLiveData()
    val isSignedIn: MutableLiveData<Boolean> = User.isSignedInLD

    private val errorMessage = "Error connecting to server"

    private val disposable = CompositeDisposable()

    fun signin(loginRequest: LoginRequest){
        status.value = Resource.loading()

        disposable.add(api.signin(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.code() == 200){
                        JwtToken.jwtToken = it.headers()["Authorization"]!!
                        isSignedIn.value = true
                        status.value = Resource.success("Welcome ${loginRequest.username}")
                    }else{
                        signOut()
                        status.value = Resource.error("Wrong credentials")
                    }
                    clearStatus()
                },
                { signOut() }
            ))

    }

    fun signup(user: User){
        status.value = Resource.loading()

        disposable.add(api.signup(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.code() == 200){
                        status.value = Resource.success(user, "Account created")
                    }else{
                        status.value = Resource.error("Something went wrong")
                    }
                    clearStatus()
                },
                { status.value = Resource.error(errorMessage) }
            ))
    }

    fun signOut(){
        isSignedIn.value = false
        JwtToken.clear()
        clearStatus()
    }

    fun getServerStatus(){
        //service.getServerStatus()
    }

    private fun clearStatus(){
        status.value = Resource.none()
    }
}