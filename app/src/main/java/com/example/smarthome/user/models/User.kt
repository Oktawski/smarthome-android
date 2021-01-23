package com.example.smarthome.user.models

import com.example.smarthome.relays.models.Relay

data class User(
        val username: String,
        val email: String,
        val password: String,
        val relayList: List<Relay>?
){
    companion object{
        var isSignedIn: Boolean = false
    }

    constructor(email: String, password: String)
        :this(email, email, password, null)

    constructor(username: String, email: String, password: String)
        :this(username, email, password, null)
}