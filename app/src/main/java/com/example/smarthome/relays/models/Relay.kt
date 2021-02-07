package com.example.smarthome.relays.models

import com.example.smarthome.WifiDevice


data class Relay(
        var id: Long?
) : WifiDevice() {
    constructor(name: String, ip: String, on: Boolean)
            : this(null){
        super.setName(if (name != "") name else "No name")
        super.setIp(ip)
        super.setOn(on)
    }



  /*  init {
        if(name == "") name = "No name" else name
        if(ip == "") ip = "No ip" else ip
    }*/
}