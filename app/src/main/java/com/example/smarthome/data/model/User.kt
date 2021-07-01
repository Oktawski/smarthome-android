package com.example.smarthome.data.model

import androidx.lifecycle.MutableLiveData

data class User(
        val username: String,
        val email: String,
        val password: String,
        val relayList: List<Relay>?
){
    companion object{
        var isSignedIn: Boolean = false
        val isSignedInLD: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    constructor(email: String, password: String)
        :this(email, email, password, null)

    constructor(username: String, email: String, password: String)
        :this(username, email, password, null)
}