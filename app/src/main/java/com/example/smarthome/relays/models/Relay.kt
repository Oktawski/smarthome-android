package com.example.smarthome.relays.models

import com.example.smarthome.R
import com.example.smarthome.adapter.ViewType
import com.example.smarthome.WifiDevice


data class Relay(
        var id: Long?
) : WifiDevice(), ViewType {

    constructor(name: String, ip: String, on: Boolean)
            : this(null){
        super.setName(if (name != "") name else "No name")
        super.setIp(ip)
        super.setOn(on)
    }

    override fun getViewType(): Int {
       return R.layout.item_relay
    }
}