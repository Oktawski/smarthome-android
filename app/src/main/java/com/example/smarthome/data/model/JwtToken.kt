package com.example.smarthome.data.model

class JwtToken(){
    companion object {
        var jwtToken: String = ""

        fun clear(){
            jwtToken = ""
        }
    }
}