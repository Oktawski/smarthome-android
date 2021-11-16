package com.example.smarthome.data.model

import com.example.smarthome.R
import com.example.smarthome.utilities.ViewType
import com.google.gson.annotations.SerializedName


data class Relay(
        var id: Long?
) : WifiDevice()
{
    constructor(name: String, mac: String, on: Boolean)
            : this(null){
        super.setName(if (name != "") name else "No name")
        super.setMac(mac)
        super.setOn(on)
    }

    override fun getViewType(): Int = R.layout.item_relay
}