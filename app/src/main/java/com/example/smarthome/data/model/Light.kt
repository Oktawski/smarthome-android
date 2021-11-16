package com.example.smarthome.data.model

import com.example.smarthome.R

data class Light(
        val id: Long?,
        val red: Int,
        val green: Int,
        val blue: Int,
        val intensity: Int
) : WifiDevice()
{
    constructor(name: String, mac: String, on: Boolean,
                red: Int, green: Int, blue: Int, intensity: Int)
        :this(null, red, green, blue, intensity){
        super.setName(if (name != "") name else "No name")
        super.setMac(mac)
        super.setOn(on)
    }

    override fun getViewType(): Int = R.layout.item_light
}
