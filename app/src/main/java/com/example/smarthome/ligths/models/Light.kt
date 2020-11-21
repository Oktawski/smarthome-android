package com.example.smarthome.ligths.models

data class Light(
        val id: Long?,
        var name: String,
        var ip: String,
        val on: Boolean,
        val red: Short,
        val green: Short,
        val blue: Short,
        val intensity: Short
) {
    constructor(name: String, ip: String, on: Boolean,
                red: Short, green: Short, blue: Short, intensity: Short)
        :this(null, name, ip, on, red, green, blue, intensity)

    init{
        if(name == "") name = "No name" else name
        if(ip == "") ip = "No ip" else ip
    }
}