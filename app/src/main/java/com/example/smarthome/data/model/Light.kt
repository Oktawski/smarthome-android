package com.example.smarthome.data.model

import com.example.smarthome.utilities.ViewType

data class Light(
        val id: Long?,
        val red: Short,
        val green: Short,
        val blue: Short,
        val intensity: Short
) : WifiDevice(), ViewType {
    constructor(name: String, ip: String, on: Boolean,
                red: Short, green: Short, blue: Short, intensity: Short)
        :this(null, red, green, blue, intensity){
        super.setName(if (name != "") name else "No name")
        super.setIp(ip)
        super.setOn(on)
    }

    override fun getViewType(): Int {
        return 0 //TODO R.layout.item_light
    }
}