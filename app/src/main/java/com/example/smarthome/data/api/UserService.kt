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
import javax.inject.Inject

class UserService @Inject constructor(
    private val api: UserEndpoints
) {

    private val _status = MutableLiveData<Resource<User>>()
            val status: LiveData<Resource<User>> get() = _status
    val serverStatus = MutableLiveData(false)
    val isSignedIn = User.isSignedIn

    private val disposable = CompositeDisposable()
    private val errorMessage = "Error connecting to server"

    fun signin(loginRequest: LoginRequest){
        _status.value = Resource.loading()

        disposable.add(api.signin(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.code() == 200){
                        JwtToken.jwtToken = it.headers()["Authorization"]!!
                        isSignedIn.value = true
                        _status.value = Resource.success("Welcome ${loginRequest.username}")
                    }else{
                        signOut()
                        _status.value = Resource.error("Wrong credentials")
                    }
                    clearStatus()
                },
                { signOut() }
            ))

    }

    fun signup(user: User){
        _status.value = Resource.loading()

        disposable.add(api.signup(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.code() == 200){
                        _status.value = Resource.success(user, "Account created")
                    }else{
                        _status.value = Resource.error("Something went wrong")
                    }
                    clearStatus()
                },
                { _status.value = Resource.error(errorMessage) }
            ))
    }

    fun signOut(){
        isSignedIn.value = false
        JwtToken.clear()
        clearStatus()
    }

    fun getServerStatus() = api.getServerStatus()

    private fun clearStatus(){
        _status.value = Resource.none()
    }

}