package com.example.smarthome.data.model

import com.example.smarthome.R
import com.example.smarthome.utilities.ViewType


data class Relay(
        var id: Long?
) : WifiDevice(),
    ViewType
{
    constructor(name: String, mac: String, on: Boolean)
            : this(null){
        super.setName(if (name != "") name else "No name")
        super.setMac(mac)
        super.setOn(on)
    }

    override fun getViewType(): Int {
       return R.layout.item_relay
    }
}