package com.example.smarthome.user.models

class JwtToken(){
    companion object {
        var jwtToken: String = ""

        fun clear(){
            this.jwtToken = ""
        }
    }
}