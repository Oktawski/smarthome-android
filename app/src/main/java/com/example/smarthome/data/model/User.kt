package com.example.smarthome.data.model

import androidx.lifecycle.MutableLiveData

data class User(
        val username: String,
        val email: String,
        val password: String,
        val relayList: List<Relay>?
){
    companion object{
        val isSignedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    constructor(username: String, email: String, password: String)
        :this(username, email, password, null)
}