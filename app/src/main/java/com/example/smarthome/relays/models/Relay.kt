package com.example.smarthome.relays.models


data class Relay(
        val id: Long?,
        var name: String,
        var ip: String,
        val on: Boolean
) {
    constructor(name: String, ip: String, on: Boolean)
        :this(null, name, ip, on)

    init {
        if(name == "") name = "No name" else name
        if(ip == "") ip = "No ip" else ip
    }
}